package org.example.data.jpa.domainEntities;

import lombok.experimental.SuperBuilder;
import org.example.data.jpa.BaseJpaEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Table(name = "detail") @AttributeOverride(name = "id", column = @Column(name = "detail_id"))
public class DetailEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private ReplaceableComponentType category;
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(
            name="detail_compatible_models",
            joinColumns = @JoinColumn(name = "detail_id")
    )
    @Column(name = "model_id", nullable = false)
    private Set<UUID> compatibleModels;

    public boolean isCompatibleWith(CarModelEntity carModelEntity){
        return compatibleModels.contains(carModelEntity.getId());
    }
}
