package ru.burunsuzian.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.burunsuzian.data.jpa.OutboxMessageEntity;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WarehouseAcceptanceRequestScheduler {
    private final OutboxMessageRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Tracer tracer;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void processOutbox() {
        List<OutboxMessageEntity> messages = outboxRepository.findByProcessedFalse();
        for (OutboxMessageEntity msg : messages) {
            Span span = null;
            if (msg.getTraceId() != null && msg.getSpanId() != null){
                TraceContext parentContext = tracer.traceContextBuilder()
                        .traceId(msg.getTraceId())
                        .spanId(msg.getSpanId())
                        .sampled(true)
                        .build();
                span = tracer.spanBuilder().setParent(parentContext).name("outbox_publisher").start();
            } else {
                span = tracer.nextSpan().name("outbox_publisher").start();
            }

            try(Tracer.SpanInScope ws = tracer.withSpan(span)){
                if ("WarehouseAcceptanceRequest".equals(msg.getEventType())) {
                    rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, "order.paid", msg.getPayload());
                }
                msg.setProcessed(true);
                outboxRepository.save(msg);
            } finally {
                span.end();
            }
        }
    }
}
