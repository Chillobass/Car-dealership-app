package ru.burunsuzian.businessLogic.details.replaceable;

import java.util.UUID;

public abstract class Wheels extends Replaceable {
    public Wheels(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}