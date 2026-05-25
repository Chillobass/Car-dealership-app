package ru.burunsuzian.data.jpa;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "outbox_messages")
@Getter @Setter @NoArgsConstructor @SuperBuilder
@AttributeOverride(name = "id", column = @Column(name = "outbox_message_id"))
public class OutboxMessageEntity extends BaseJpaEntity {
    @Column(name = "event_type")
    private String eventType;
    private String payload;
    private String stat;
    private int attempts;
    @Column(name = "car_order_id")
    private UUID carOrderId;

    @Column(name = "trace_id")
    private String traceId;

    @Column(name = "span_id")
    private String spanId;

    @Builder.Default
    private boolean processed = false;
}