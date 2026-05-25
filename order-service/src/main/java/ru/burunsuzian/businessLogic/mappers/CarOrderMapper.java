package ru.burunsuzian.businessLogic.mappers;

import ru.burunsuzian.application.responses.CarOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ru.burunsuzian.businessLogic.domain.carOrder.CarOrder;
import ru.burunsuzian.businessLogic.domain.carOrder.CustomOrderState;
import ru.burunsuzian.businessLogic.domain.carOrder.DefaultOrderState;
import ru.burunsuzian.businessLogic.domain.carOrder.OrderState;
import ru.burunsuzian.data.jpa.CarOrderEntity;
import ru.burunsuzian.data.jpa.CustomOrderStateEntity;
import ru.burunsuzian.data.jpa.DefaultOrderStateEntity;
import ru.burunsuzian.data.jpa.OrderStateEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CarOrderMapper {
    @Mapping(target = "carOrderId", source = "id")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "managerId", source = "managerId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "orderState", expression = "java(carOrderEntity.getOrderState().toDomain())")
    CarOrder toDomain(CarOrderEntity carOrderEntity);

    @Mapping(target = "id", source = "carOrderId")
    @Mapping(target = "managerId", source = "managerId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "orderState", expression = "java(orderStateToEntity(carOrder.getOrderState()))")
    CarOrderEntity toEntity(CarOrder carOrder);

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "managerId", source = "managerId")
    @Mapping(target = "carId", source = "carId")
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
