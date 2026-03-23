package data.jpa;

import businessLogic.CarModel;
import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;
import businessLogic.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cars")
public class CarEntity extends BaseJpaEntity {
    @Column(name="car_model") @JoinColumn(name="car_model_id")
    private CarModel model;
    @Column(name="car_id")
    private UUID carID;

    @ElementCollection
    @CollectionTable(name="configuration", joinColumns = @JoinColumn(name="config_id"))
    private HashMap<Replaceable, Detail> config;

    public Money calculatePrice(){
        Money totalPrice = getModel().getBasePrice();

        for (var i : config.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
