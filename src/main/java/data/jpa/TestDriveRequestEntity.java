package data.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name = "test_drive_request")
public class TestDriveRequestEntity extends BaseJpaEntity {
    private String customer;

    @OneToMany @JoinColumn(name="carID")
    @Column(name = "ordered_car_id")
    private String orderedCar;
    private Timestamp time; // TestDrive start time
    private boolean finished;
}
