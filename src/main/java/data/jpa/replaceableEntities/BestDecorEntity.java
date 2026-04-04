package data.jpa.replaceableEntities;

import businessLogic.details.replaceable.BestDecor;
import businessLogic.details.replaceable.Replaceable;
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
