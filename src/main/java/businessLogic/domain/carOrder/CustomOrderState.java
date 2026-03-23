package businessLogic.domain.carOrder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public enum CustomOrderState implements OrderState {
    CANCELLED(),
    COMPLETED(),
    CAR_READY(COMPLETED, CANCELLED),
    AWAITING_CAR_DELIVERY(CAR_READY, CANCELLED),
    PAID(AWAITING_CAR_DELIVERY, CANCELLED),
    AWAITING_PAYMENT(PAID, CANCELLED),
    WAREHOUSE_APPROVED(AWAITING_PAYMENT, CANCELLED),
    PLACED(WAREHOUSE_APPROVED, CANCELLED);

    private final Set<CustomOrderState> allowedTransitions;

    CustomOrderState(CustomOrderState... allowed) {
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
        if (!(next instanceof CustomOrderState)) return false;
        return allowedTransitions.contains(next);
    }
}