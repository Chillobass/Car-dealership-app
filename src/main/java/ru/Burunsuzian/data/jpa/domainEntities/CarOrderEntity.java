package ru.Burunsuzian.data.jpa.domainEntities;

import ru.Burunsuzian.data.jpa.BaseJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "car_order")
public class CarOrderEntity extends BaseJpaEntity {
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "manager_id")
    private UUID managerId;

    @Column(name = "ordered_car_id")
    private UUID orderedCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_state")
    private OrderStateEntity orderState;
}