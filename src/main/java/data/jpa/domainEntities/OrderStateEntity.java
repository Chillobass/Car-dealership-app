package data.jpa.domainEntities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import businessLogic.domain.carOrder.OrderState;
import data.jpa.BaseJpaEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_state")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "order_state")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "state"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DefaultOrderStateEntity.class, name = "default_order_state"),
    @JsonSubTypes.Type(value = CustomOrderStateEntity.class, name = "custom_order_state")
})
@Getter
public abstract class OrderStateEntity extends BaseJpaEntity {
    private String state;

    public abstract OrderState toDomain();
    
}
