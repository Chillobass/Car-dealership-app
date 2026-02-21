package BusinessLogic;

import BusinessLogic.Exceptions.IncompatibleComponentException;
import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;
import data.Domain.valueObjects.Money;

import java.util.HashMap;
import java.util.Map;

public class CarConfiguration {
    private CarModel carModel;
    private Map<DetailCategory, Detail> swappedDetails;

    public CarConfiguration(Car someCar){
        this.carModel = someCar.getModel();
        this.swappedDetails = new HashMap<>(someCar.getModel().getBaseDetails());
    }

    public void swapDetail(Detail detail){
        if (detail.isCompatibleWith(carModel) == false){
            throw new IncompatibleComponentException("detail " + detail.getCategory() + " is not compatible with " + carModel.getModelName());
        }
        swappedDetails.remove(detail.getCategory());
        swappedDetails.put(detail.getCategory(), detail);
    }
    public Money calculatePrice(){
        Money totalPrice = carModel.getBasePrice();

        for (var i : swappedDetails.values()){
            totalPrice.Add(i.getPrice());
        }
        return totalPrice;
    }
}
