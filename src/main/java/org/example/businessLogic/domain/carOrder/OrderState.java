package org.example.businessLogic.domain.carOrder;

public interface OrderState {
    boolean canTransitionTo(OrderState next);

    String getName();
}