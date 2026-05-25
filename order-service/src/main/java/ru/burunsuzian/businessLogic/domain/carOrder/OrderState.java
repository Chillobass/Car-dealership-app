package ru.burunsuzian.businessLogic.domain.carOrder;

public interface OrderState {
    boolean canTransitionTo(OrderState next);

    String getName();
}