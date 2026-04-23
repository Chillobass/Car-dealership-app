package ru.Burunsuzian.businessLogic.domain.carOrder;

public interface OrderState {
    boolean canTransitionTo(OrderState next);

    String getName();
}