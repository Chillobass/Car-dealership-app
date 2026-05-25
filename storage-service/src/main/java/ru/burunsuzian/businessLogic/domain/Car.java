package ru.burunsuzian.businessLogic.domain;

import ru.burunsuzian.businessLogic.details.Detail;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.businessLogic.domain.valueObjects.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter @Builder
public class Car {
    private CarModel model;
    private UUID carId;
    @Setter
    private boolean configured = false;
    @Setter
    private HashMap<Replaceable, Detail> config;

    public Money calculatePrice(){
        Money totalPrice = getModel().getBasePrice();

        for (var i : config.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
