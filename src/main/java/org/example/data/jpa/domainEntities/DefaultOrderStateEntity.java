package org.example.data.jpa.domainEntities;

import org.example.businessLogic.domain.carOrder.OrderState;
import org.example.businessLogic.domain.carOrder.DefaultOrderStateToString;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("default_order_state")
@AllArgsConstructor
public class DefaultOrderStateEntity extends OrderStateEntity {
    public DefaultOrderStateEntity(String state) {
        super(state);
    }

    @Override
    public OrderState toDomain() {
        var converter = new DefaultOrderStateToString();
        return converter.convert(this.getState());
    }
}
