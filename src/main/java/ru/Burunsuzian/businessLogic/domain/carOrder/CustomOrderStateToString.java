package ru.Burunsuzian.businessLogic.domain.carOrder;

import java.util.HashMap;
import java.util.Map;

public class CustomOrderStateToString {
    private Map<String, CustomOrderState> stringToStateMap = new HashMap<>() {
        {
            put("CANCELLED", CustomOrderState.CANCELLED);
            put("COMPLETED", CustomOrderState.COMPLETED);
            put("CAR_READY", CustomOrderState.CAR_READY);
            put("AWAITING_CAR_DELIVERY", CustomOrderState.AWAITING_CAR_DELIVERY);
            put("PAID", CustomOrderState.PAID);
            put("AWAITING_PAYMENT", CustomOrderState.AWAITING_PAYMENT);
            put("WAREHOUSE_APPROVED", CustomOrderState.WAREHOUSE_APPROVED);
            put("PLACED", CustomOrderState.PLACED);
        }
    };
    public CustomOrderState convert(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State string cannot be null");
        }
        CustomOrderState orderState = stringToStateMap.get(state.toUpperCase());
        if (orderState == null) {
            throw new IllegalArgumentException("Invalid state string: " + state);
        }
        return orderState;
    }
}
