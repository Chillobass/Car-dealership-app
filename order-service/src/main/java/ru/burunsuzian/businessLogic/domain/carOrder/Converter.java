package ru.burunsuzian.businessLogic.domain.carOrder;

import java.util.HashMap;
import java.util.Map;

public class Converter {
    private static Map<String, DefaultOrderState> DefaultStringToStateMap = new HashMap<>() {
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

    private static Map<String, CustomOrderState> CustomStringToStateMap = new HashMap<>() {
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
    public static OrderState convert(String state, boolean isCustomCar){
        if (state == null) {
            throw new IllegalArgumentException("State string cannot be null");
        }
        if (isCustomCar){
            CustomOrderState orderState = CustomStringToStateMap.get(state.toUpperCase());
            if (orderState == null) {
                throw new IllegalArgumentException("Invalid state string: " + state);
            }
            return orderState;
        }
        DefaultOrderState orderState = DefaultStringToStateMap.get(state.toUpperCase());
        if (orderState == null) {
            throw new IllegalArgumentException("Invalid state string: " + state);
        }
        return orderState;

    }
}
