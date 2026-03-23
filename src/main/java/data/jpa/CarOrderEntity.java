package data.jpa;

import businessLogic.Car;
import businessLogic.domain.User;
import businessLogic.domain.carOrder.OrderState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor
@Table(name = "car_order")
public class CarOrderEntity extends BaseJpaEntity {
    @Column(name = "customer_id")
    private User customerId;

    @Column(name = "manager_id")
    private User managerId;

    @Column(name = "ordered_car_model_id")
    private Car orderedCarModelId;

    @Column(name = "order_state")
    private String orderState;
}