package ru.burunsuzian.businessLogic.details.replaceable;

import java.util.UUID;

public abstract class Decor extends Replaceable {
    public Decor(UUID id, UUID reservedBy) {
        super(id, reservedBy);
    }
}
