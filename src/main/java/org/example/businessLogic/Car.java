package org.example.businessLogic;

import org.example.businessLogic.details.*;
import org.example.businessLogic.details.Detail;
import org.example.businessLogic.details.replaceable.Replaceable;
import org.example.businessLogic.domain.valueObjects.Money;
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
    private boolean configured;
    private HashMap<Replaceable, Detail> config;

    public Money calculatePrice(){
        Money totalPrice = getModel().getBasePrice();

        for (var i : config.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
