package ru.burunsuzian.data.jpa.domainEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.burunsuzian.data.jpa.BaseJpaEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "warehouse_acceptance")
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class WarehouseAcceptanceEntity extends BaseJpaEntity {

    @Column(name = "car_order_id", nullable = false)
    private UUID carOrderId;

    @Column(name = "order_type", nullable = false)
    private String orderType;

    @Column(name = "car_id")
    private UUID carId;

    @Column(name = "car_model_id")
    private UUID carModelId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "warehouse_acceptance_components",
            joinColumns = @JoinColumn(name = "acceptance_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<ReplaceableComponentType> requiredComponents;

    @Column(name = "responsible_staff_id")
    private UUID responsibleStaffId;

    @Column(name = "state", nullable = false)
    private String state;
}