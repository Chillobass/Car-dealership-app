package ru.Burunsuzian.businessLogic;

import ru.Burunsuzian.businessLogic.details.*;
import ru.Burunsuzian.businessLogic.details.Detail;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter @Builder
public class Car {
    private CarModel model;
    private UUID carID;
    @Setter
    private boolean configured = false;
    private HashMap<Replaceable, Detail> config;

    public Money calculatePrice(){
        Money totalPrice = getModel().getBasePrice();

        for (var i : config.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
