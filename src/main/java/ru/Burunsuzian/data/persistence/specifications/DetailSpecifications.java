package ru.Burunsuzian.data.persistence.specifications;

import ru.Burunsuzian.businessLogic.details.Detail;
import ru.Burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.Burunsuzian.businessLogic.domain.valueObjects.Money;

public class DetailSpecifications {
    public static Specification<Detail> byCategory(Replaceable categoty){
        return detail -> (categoty.equals(detail.getCategory()));
    }

    public static Specification<Detail> byPriceLessThanOrEqual(Money maxPrice) {
        return detail -> {
            Money price = detail.getPrice();
            return price != null && price.getMoney().compareTo(maxPrice.getMoney()) <= 0;
        };
    }

    public static Specification<Detail> byPriceGreaterThanOrEqual(Money minPrice) {
        return detail -> {
            Money price = detail.getPrice();
            return price != null && price.getMoney().compareTo(minPrice.getMoney()) >= 0;
        };
    }
}
