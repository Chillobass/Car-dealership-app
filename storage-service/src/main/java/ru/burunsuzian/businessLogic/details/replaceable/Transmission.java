package ru.burunsuzian.businessLogic.details.replaceable;

import java.util.UUID;

public abstract class Transmission extends Replaceable {
    public Transmission(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}