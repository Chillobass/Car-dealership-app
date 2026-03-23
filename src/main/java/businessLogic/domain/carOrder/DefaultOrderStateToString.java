package businessLogic.domain.carOrder;

import java.util.HashMap;
import java.util.Map;

public class DefaultOrderStateToString {
    private Map<String, DefaultOrderState> stringToStateMap = new HashMap<>() {
        {
            put("CANCELLED", DefaultOrderState.CANCELLED);
            put("COMPLETED", DefaultOrderState.COMPLETED);
            put("CAR_READY", DefaultOrderState.CAR_READY);
            put("PAID", DefaultOrderState.PAID);
            put("AWAITING_PAYMENT", DefaultOrderState.AWAITING_PAYMENT);
            put("MANAGER_APPROVED", DefaultOrderState.MANAGER_APPROVED);
            put("PLACED", DefaultOrderState.PLACED);
        }
    };
    public DefaultOrderState convert(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State string cannot be null");
        }
        DefaultOrderState orderState = stringToStateMap.get(state.toUpperCase());
        if (orderState == null) {
            throw new IllegalArgumentException("Invalid state string: " + state);
        }
        return orderState;
    }
}
