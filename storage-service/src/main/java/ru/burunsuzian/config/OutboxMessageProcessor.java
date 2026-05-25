package ru.burunsuzian.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.tools.Trace;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.burunsuzian.data.jpa.OutboxMessageEntity;
import ru.burunsuzian.data.persistence.repositories.OutboxMessageRepository;

@Service
@RequiredArgsConstructor
public class OutboxMessageProcessor {

    private final OutboxMessageRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Tracer tracer;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processSingleMessage(OutboxMessageEntity msg) {
        Span span = null;
        if (msg.getTraceId() != null && msg.getSpanId() != null){
            TraceContext parentContext = tracer.traceContextBuilder()
                    .traceId(msg.getTraceId())
                    .spanId(msg.getSpanId())
                    .sampled(true)
                    .build();
            span = tracer.spanBuilder().setParent(parentContext).name("outbox_publisher").start();
        } else{
            span = tracer.nextSpan().name("outbox_publisher").start();
        }

        try(Tracer.SpanInScope ws = tracer.withSpan(span)){
            if ("WAREHOUSE_APPROVAL_RESPONSE".equals(msg.getEventType())) {
                rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_STORAGE, "storage.result", msg.getPayload());
            }
            msg.setProcessed(true);
            outboxRepository.save(msg);
        } finally{
            span.end();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFailure(OutboxMessageEntity msg) {
        msg.setAttempts(msg.getAttempts() + 1);
        if (msg.getAttempts() >= 5) {
            msg.setProcessed(true);
            msg.setStat("ERROR");
        }
        outboxRepository.save(msg);
    }
}
