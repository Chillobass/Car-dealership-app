package ru.burunsuzian.businessLogic.details.replaceable;

import java.util.UUID;

public abstract class SteeringWheel extends Replaceable {
    public SteeringWheel(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}
