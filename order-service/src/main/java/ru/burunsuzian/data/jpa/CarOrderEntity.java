package ru.burunsuzian.data.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "car_order")
@AttributeOverride(name = "id", column = @Column(name = "car_order_id"))
public class CarOrderEntity extends BaseJpaEntity {
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "ordered_car_id")
    private UUID carId;

    @Column(name = "is_custom_car")
    private boolean isCustomCar;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_state")
    private OrderStateEntity orderState;
}