package application;

import application.dto.CarOrderDto;
import application.dto.OrderStateDto;
import businessLogic.dtoToDomainMappers.CarOrderDtoMapper;
import data.persistence.services.CarOrderService;

public class UserController {
    private CarOrderService carOrderService;
    public void createOrder(CarOrderDto dto){
        carOrderService.createOrder(dto.customer(), dto.manager(), dto.orderedCar());
    }

//    public void transitionTo(CarOrderDto dto, OrderStateDto OSdto){
//        carOrderService.transitionTo(CarOrderDtoMapper.MapToCarOrder(dto));
//    }
    // dto enum'а...
}
