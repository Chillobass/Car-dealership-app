package ru.burunsuzian.data.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "test_drive_request")
public class TestDriveRequestEntity extends BaseJpaEntity {
    private UUID customerId;

    @Column(name = "ordered_car_id")
    private UUID orderedCar;
    private Instant time;
    private boolean finished;
}
