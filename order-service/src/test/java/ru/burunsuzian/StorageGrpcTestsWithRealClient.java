package ru.burunsuzian;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.burunsuzian.application.CarGrpcDto;
import ru.burunsuzian.businessLogic.exceptions.StorageServiceUnavailableException;
import ru.burunsuzian.grpc.StorageCarsGrpcClient;
import ru.burunsuzian.grpc.storage.v1.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

public class StorageGrpcTestsWithRealClient extends BaseIntegrationTest{
    @Autowired
    private StorageCarsGrpcClient client;
    private Server inProcessServer;
    private ManagedChannel inProcessChannel;
    private Object originalStub;

    private void startServerWith(StorageCarsServiceGrpc.StorageCarsServiceImplBase service) throws Exception{
        String serverName = "in-process" + UUID.randomUUID();

        inProcessServer = InProcessServerBuilder.forName(serverName)
                .addService(service).build().start();

        inProcessChannel = InProcessChannelBuilder.forName(serverName).usePlaintext().build();

        rewireClient();
    }

    private void rewireClient(){
        var newStub = StorageCarsServiceGrpc.newBlockingStub(inProcessChannel);
        if (originalStub == null){
            originalStub = ReflectionTestUtils.getField(client, "stub");
        }
        ReflectionTestUtils.setField(client, "stub", newStub);
    }

    @AfterEach
    void cleanup(){
        if (originalStub != null){
            ReflectionTestUtils.setField(client, "stub", originalStub);
            originalStub = null;
        }
        if (inProcessChannel != null){
            inProcessChannel.shutdownNow();
            inProcessChannel = null;
        }
        if (inProcessServer != null){
            inProcessServer.shutdownNow();
            inProcessServer = null;
        }
    }

    @Test
    @DisplayName("storage недсотупен 503")
    void shouldHandleUnavailableWhenServerIsDown(){
        String missingServerName = "missing-server-" + UUID.randomUUID();
        inProcessChannel = InProcessChannelBuilder.forName(missingServerName)
                .usePlaintext().build();
        rewireClient();

        assertThatThrownBy(() -> client.listAvailableCars())
                .isInstanceOf(StorageServiceUnavailableException.class);
    }

    @Test
    @DisplayName("сервер 503")
    void shouldHandleExplicitUnavailableFromServer() throws Exception {
        startServerWith(new StorageCarsServiceGrpc.StorageCarsServiceImplBase() {
            @Override
            public void getAvailableCar(GetAvailableCarRequest req, StreamObserver<GetAvailableCarResponse> resp){
                resp.onError(Status.UNAVAILABLE.withDescription("storage db in unreachable").asRuntimeException());
            }
        });
        assertThatThrownBy(() -> client.getAvailableCar(UUID.randomUUID())).isInstanceOf(StorageServiceUnavailableException.class);
    }

    void shouldHandleTimeoutWhenServerHangs() throws Exception {
        startServerWith(new StorageCarsServiceGrpc.StorageCarsServiceImplBase() {
            @Override
            public void listAvailableCars(ListAvailableCarsRequest req, StreamObserver<ListAvailableCarsResponse> resp){}
        });
        assertThatThrownBy(() -> client.listAvailableCars()).isInstanceOf(StorageServiceUnavailableException.class);
    }

    @Test
    void shouldHandleEmptyResultGracefully() throws Exception{
        startServerWith(new StorageCarsServiceGrpc.StorageCarsServiceImplBase() {
            @Override
            public void listAvailableCars(ListAvailableCarsRequest req, StreamObserver<ListAvailableCarsResponse> resp){
                ListAvailableCarsResponse empty = ListAvailableCarsResponse.newBuilder()
                        .addAllCars(List.<CarProto>of())
                        .build();
                resp.onNext(empty);
                resp.onCompleted();
            }
        });
        List<CarGrpcDto> result = client.listAvailableCars();

        assertThat(result).isEmpty();
    }
}
