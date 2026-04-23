package ru.Burunsuzian.businessLogic.Mappers;

import ru.Burunsuzian.application.responses.CarOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ru.Burunsuzian.businessLogic.domain.carOrder.CarOrder;
import ru.Burunsuzian.businessLogic.domain.carOrder.CustomOrderState;
import ru.Burunsuzian.businessLogic.domain.carOrder.DefaultOrderState;
import ru.Burunsuzian.businessLogic.domain.carOrder.OrderState;
import ru.Burunsuzian.data.jpa.domainEntities.CarOrderEntity;
import ru.Burunsuzian.data.jpa.domainEntities.CustomOrderStateEntity;
import ru.Burunsuzian.data.jpa.domainEntities.DefaultOrderStateEntity;
import ru.Burunsuzian.data.jpa.domainEntities.OrderStateEntity;

@Mapper(componentModel = "spring", uses = {CarMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CarOrderMapper {
    @Mapping(target = "customer.userId", source = "customerId")
    @Mapping(target = "manager.userId", source = "managerId")
    @Mapping(target = "orderedCar.carID", source = "orderedCar")
    @Mapping(target = "orderState", expression = "java(carOrderEntity.getOrderState().toDomain())")
    CarOrder toDomain(CarOrderEntity carOrderEntity);

    @Mapping(target = "customerId", source = "customer.userId")
    @Mapping(target = "managerId", source = "manager.userId")
    @Mapping(target = "orderedCar", source = "orderedCar.carID")
    @Mapping(target = "orderState", expression = "java(orderStateToEntity(carOrder.getOrderState()))")
    CarOrderEntity toEntity(CarOrder carOrder);

    @Mapping(target = "customerId", source = "customer.userId")
    @Mapping(target = "managerId", source = "manager.userId")
    @Mapping(target = "carId", source = "orderedCar.carID")
    @Mapping(target = "state", expression = "java(carOrder.getOrderState().getName())")
    CarOrderResponse toDto(CarOrder carOrder);

    @Named("orderStateToEntity")
    default OrderStateEntity orderStateToEntity(OrderState orderState) {
        if (orderState instanceof DefaultOrderState) {
            return new DefaultOrderStateEntity(orderState.toString());
        }
        else if (orderState instanceof CustomOrderState) {
            return new CustomOrderStateEntity(orderState.toString());
        }
        else {
            throw new IllegalArgumentException("Unknown OrderState type: " + orderState.getClass());
        }
    }
}
