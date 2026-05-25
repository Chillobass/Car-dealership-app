package ru.burunsuzian.businessLogic.mappers;

import ru.burunsuzian.businessLogic.details.replaceable.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FromStringToReplaceable {
    private static Map<String, Replaceable> stringToReplaceableMap = new HashMap<>() {
        {
            put("AutomaticTransmission", new AutomaticTransmission(null, UUID.randomUUID()));
            put("BestDecor", new BestDecor(null, UUID.randomUUID()));
            put("RoundSteeringWheel", new RoundSteeringWheel(null, UUID.randomUUID()));
            put("HotWheels", new HotWheels(null, UUID.randomUUID()));
            put("ManualTransmission", new ManualTransmission(null, UUID.randomUUID()));
        }
    };
    public static Replaceable convert(String detail) {
        if (detail == null) {
            throw new IllegalArgumentException("detail string cannot be null");
        }

        Replaceable getDetail = stringToReplaceableMap.get(detail);
        if (getDetail == null) {
            throw new IllegalArgumentException("Invalid detail string: " + getDetail);
        }
        return getDetail;
    }
}
