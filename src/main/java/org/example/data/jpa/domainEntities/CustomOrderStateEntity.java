package org.example.data.jpa.domainEntities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.businessLogic.domain.carOrder.OrderState;
import org.example.businessLogic.domain.carOrder.CustomOrderStateToString;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@AllArgsConstructor
@DiscriminatorValue("custom_order_state")
public class CustomOrderStateEntity extends OrderStateEntity {
    public CustomOrderStateEntity(String state) {
        super(state);
    }

    @Override
    public OrderState toDomain() {
        var converter = new CustomOrderStateToString();
        return converter.convert(getState());
    }
}
