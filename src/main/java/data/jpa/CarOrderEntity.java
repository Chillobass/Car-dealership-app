package data.jpa;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.domain.carOrder.OrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name = "car_order")
public class CarOrderEntity extends BaseJpaEntity {
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "manager_id")
    private UUID managerId;

    @OneToOne @JoinColumn(name="carID")
    @Column(name = "ordered_car_model_id")
    private Car orderedCarModelId;

    @Column(name = "order_state")
    private String orderState;
}