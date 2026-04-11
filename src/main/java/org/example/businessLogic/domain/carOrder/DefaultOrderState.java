package org.example.businessLogic.domain.carOrder;
import java.util.Set;
import java.util.Arrays;
import java.util.Objects;

public enum DefaultOrderState implements OrderState {
    CANCELLED(),
    COMPLETED(),
    CAR_READY(COMPLETED, CANCELLED),
    PAID(CAR_READY, CANCELLED),
    AWAITING_PAYMENT(PAID, CANCELLED),
    MANAGER_APPROVED(AWAITING_PAYMENT, CANCELLED),
    PLACED(MANAGER_APPROVED, CANCELLED);

    private final Set<DefaultOrderState> allowedTransitions;

    DefaultOrderState(DefaultOrderState... allowed) {
        if (allowed == null || allowed.length == 0) {
            this.allowedTransitions = Set.of();
        } else if (allowed.length == 1) {
            this.allowedTransitions = Set.of(Objects.requireNonNull(allowed[0]));
        } else {
            this.allowedTransitions = Set.of(Arrays.copyOf(allowed, allowed.length));
        }
    }

    @Override
    public boolean canTransitionTo(OrderState next) {
        if (!(next instanceof DefaultOrderState)) return false;
        return allowedTransitions.contains(next);
    }

    @Override
    public String getName(){
        return this.name();
    }
}
