package ru.burunsuzian.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.burunsuzian.data.jpa.OutboxMessageEntity;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WarehouseApprovalResponseScheduler {

    private final OutboxMessageRepository outboxRepository;
    private final OutboxMessageProcessor messageProcessor;

    @Scheduled(fixedDelay = 2000)
    public void processOutbox() {
        List<OutboxMessageEntity> messages = outboxRepository.findByProcessedFalse();

        for (OutboxMessageEntity msg : messages) {
            try {
                messageProcessor.processSingleMessage(msg);
            } catch (Exception e) {
                log.error("Failed to process outbox message with id: {}", msg.getId(), e);
                messageProcessor.handleFailure(msg);
            }
        }
    }
}