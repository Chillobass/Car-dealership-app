package ru.burunsuzian.data.jpa;

import lombok.AllArgsConstructor;
import ru.burunsuzian.businessLogic.domain.carOrder.Converter;
import ru.burunsuzian.businessLogic.domain.carOrder.OrderState;
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
        return Converter.convert(getState(), true);
    }
}
