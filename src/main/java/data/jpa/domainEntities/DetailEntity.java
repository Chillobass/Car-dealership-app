package data.jpa.domainEntities;

import data.jpa.BaseJpaEntity;
import data.jpa.replaceableEntities.ReplaceableComponentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "detail") @AttributeOverride(name = "id", column = @Column(name = "detail_id"))
public class DetailEntity extends BaseJpaEntity {
    @ManyToOne
    @JoinColumn(name = "category")
    private ReplaceableComponentType category;
    private BigDecimal price;

    @ManyToMany
    @JoinTable(
            name="detail_compatible_models",
            joinColumns = @JoinColumn(name = "detail_id"), // внешний ключ на DetailEntity
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<UUID> compatibleModels;

    public boolean isCompatibleWith(CarModelEntity carModelEntity){
        return compatibleModels.contains(carModelEntity.getId());
    }
}
