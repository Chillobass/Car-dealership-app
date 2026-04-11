package org.example.data.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @MappedSuperclass @SuperBuilder @EntityListeners(AuditingEntityListener.class)
public abstract class BaseJpaEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="created_at", updatable = false, nullable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
