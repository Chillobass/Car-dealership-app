package data.jpa.domainEntities;

import data.jpa.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "test_drive_request")
public class TestDriveRequestEntity extends BaseJpaEntity {
    private UUID customerId;

    @OneToMany @JoinColumn(name="carID")
    @Column(name = "ordered_car_id")
    private UUID orderedCar;
    private Timestamp time; // TestDrive start time
    private boolean finished;
}
