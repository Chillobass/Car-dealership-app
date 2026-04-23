package ru.Burunsuzian.businessLogic.Mappers;

import ru.Burunsuzian.businessLogic.details.replaceable.*;
import ru.Burunsuzian.businessLogic.details.replaceable.*;

import java.util.HashMap;
import java.util.Map;

public class FromStringToReplaceable {
    private static Map<String, Replaceable> stringToReplaceableMap = new HashMap<>() {
        {
            put("AutomaticTransmission", new AutomaticTransmission());
            put("BestDecor", new BestDecor());
            put("RoundSteeringWheel", new RoundSteeringWheel());
            put("HotWheels", new HotWheels());
            put("ManualTransmission", new ManualTransmission());
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
