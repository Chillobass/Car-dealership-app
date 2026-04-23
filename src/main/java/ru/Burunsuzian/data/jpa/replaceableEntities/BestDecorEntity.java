package ru.Burunsuzian.data.jpa.replaceableEntities;

import ru.Burunsuzian.businessLogic.details.replaceable.BestDecor;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
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
        return new BestDecor();
    }
}
