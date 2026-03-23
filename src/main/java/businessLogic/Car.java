package businessLogic;

import businessLogic.details.*;
import businessLogic.details.replaceable.Replaceable;
import businessLogic.domain.valueObjects.Money;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter @Builder
public class Car {
    private CarModel model;
    private UUID carID;
    private HashMap<Replaceable, Detail> config;

    public Money calculatePrice(){
        Money totalPrice = getModel().getBasePrice();

        for (var i : config.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
