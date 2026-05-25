package ru.burunsuzian.config.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.burunsuzian.application.events.WarehouseApprovalRequest;
import ru.burunsuzian.config.RabbitMqConfig;
import ru.burunsuzian.data.persistence.repositories.WarehouseAcceptanceRepository;
import ru.burunsuzian.data.persistence.services.WarehouseAcceptanceService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WarehouseApprovalRequestListener {

    private final WarehouseAcceptanceService warehouseService;
    private final WarehouseAcceptanceRepository warehouseRepository;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_STORAGE_APPROVAL)
    @Transactional
    public void handleOrderPaid(WarehouseApprovalRequest request) throws JsonProcessingException {

        log.info(" Обработка запроса на сборку заказа {}", request.carOrderId());

        if (!warehouseRepository.findAllByCarOrderId(request.carOrderId()).isEmpty()) {
            log.warn("Сборка для заказа {} уже существует", request.carOrderId());
            return;
        }

        warehouseService.createWarehouseAcceptance(request.carOrderId(), request.isCustomCar());
    }
}
