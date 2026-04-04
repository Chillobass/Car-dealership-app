package businessLogic.domain.valueObjects;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Money {
    private BigDecimal money;

    public Money(BigDecimal moneyAmount){
        if (moneyAmount.signum() >= 0){
            this.money = moneyAmount;
        } else {
            throw new IllegalArgumentException("what do u mean by negative price value?");
        }
    }
    public void Add(Money moneyAmount){
        money.add(moneyAmount.money);
    }

    public Money(int moneyAmount){
        if (moneyAmount >= 0){
            this.money = BigDecimal.valueOf(moneyAmount);
        } else {
            throw new IllegalArgumentException("what do u mean by negative price value?");
        }
    }
}
