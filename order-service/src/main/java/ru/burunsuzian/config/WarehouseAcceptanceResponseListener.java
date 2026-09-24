package ru.burunsuzian.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.burunsuzian.application.events.WarehouseApprovalResponse;
import ru.burunsuzian.businessLogic.domain.carOrder.CarOrder;
import ru.burunsuzian.businessLogic.domain.carOrder.CustomOrderState;
import ru.burunsuzian.businessLogic.exceptions.EntityNotFoundException;
import ru.burunsuzian.businessLogic.mappers.CarOrderMapper;
import ru.burunsuzian.data.jpa.CarOrderEntity;
import ru.burunsuzian.data.persistence.repositories.CarOrderRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseAcceptanceResponseListener {

    private final CarOrderRepository carOrderRepository;
    private final CarOrderMapper carOrderMapper;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_ORDER_RESULT)
    @Transactional
    public void handleWarehouseResponse(WarehouseApprovalResponse response) throws JsonProcessingException {
        if (response.traceId() != null){
            MDC.put("traceId", response.traceId());
        }

        try {
            log.info("TraceID [{}]: Получен ответ от склада для заказа {}", response.traceId(), response.carOrderId());

            CarOrderEntity entity = carOrderRepository.findById(response.carOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found"));

            CarOrder domain = carOrderMapper.toDomain(entity);

            if (response.isApproved() && domain.getOrderState() == CustomOrderState.WAREHOUSE_APPROVED) {
                log.info("Заказ {} уже в статусе WAREHOUSE_APPROVED", response.carOrderId());
                return;
            }

            if (domain.getOrderState() == CustomOrderState.CAR_READY || domain.getOrderState() == CustomOrderState.CANCELLED) {
                return;
            }

            if (response.isApproved()) {
                domain.transitionTo(CustomOrderState.WAREHOUSE_APPROVED);
            } else {
                domain.transitionTo(CustomOrderState.CANCELLED);
            }

            carOrderRepository.save(carOrderMapper.toEntity(domain));
        } finally {
            MDC.remove("traceId");
        }
    }
}
