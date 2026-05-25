package ru.burunsuzian.data.jpa.replaceableEntities;

import ru.burunsuzian.businessLogic.details.replaceable.BestDecor;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("best_decor")
public class BestDecorEntity extends ReplaceableComponentType {
    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Replaceable toDomain() {
        return new BestDecor(id, reservedBy);
    }
}
