package businessLogic.domain.valueObjects;

import lombok.Getter;

import java.math.BigDecimal;

public class Money {
    @Getter
    private BigDecimal money;

    public Money(BigDecimal moneyAmount){
        if (moneyAmount.signum() >= 0){
            this.money = moneyAmount;
        } else {
            throw new IllegalArgumentException("what do u mean by negative price value?");
        }
    }
    public void Add(BigDecimal moneyAmount){
        money.add(moneyAmount);
    }
    public void Add(Money moneyAmount){
        money.add(moneyAmount.money);
    }

//    public boolean CompareTo(int anotherInteger){
//        return this.money.compareTo(new BigDecimal(anotherInteger));
//    }
}
