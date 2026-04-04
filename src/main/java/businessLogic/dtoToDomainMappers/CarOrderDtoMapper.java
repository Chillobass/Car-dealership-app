package businessLogic.dtoToDomainMappers;

import application.dto.CarOrderDto;
import businessLogic.domain.carOrder.CarOrder;

public class CarOrderDtoMapper {
    public static CarOrder MapToCarOrder(CarOrderDto dto){
        return new CarOrder(dto.customer(), dto.manager(), dto.orderedCar(), dto.orderState());
    }
}
