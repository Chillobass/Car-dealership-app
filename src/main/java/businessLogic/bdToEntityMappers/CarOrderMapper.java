package businessLogic.bdToEntityMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import businessLogic.domain.carOrder.CarOrder;
import businessLogic.domain.carOrder.CustomOrderState;
import businessLogic.domain.carOrder.DefaultOrderState;
import businessLogic.domain.carOrder.OrderState;
import data.jpa.domainEntities.CarOrderEntity;
import data.jpa.domainEntities.CustomOrderStateEntity;
import data.jpa.domainEntities.DefaultOrderStateEntity;
import data.jpa.domainEntities.OrderStateEntity;

@Mapper(componentModel = "spring", uses = {CarMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CarOrderMapper {
    @Mapping(target = "customer.userID", source = "customerId")
    @Mapping(target = "manager.userID", source = "managerId")
    @Mapping(target = "orderedCar.carID", source = "orderedCar")
    @Mapping(target = "orderState", expression = "java(carOrderEntity.getOrderState().toDomain())")
    CarOrder toDomain(CarOrderEntity carOrderEntity);

    @Mapping(target = "customerId", source = "customer.userID")
    @Mapping(target = "managerId", source = "manager.userID")
    @Mapping(target = "orderedCar", source = "orderedCar.carID")
    @Mapping(target = "orderState", expression = "java(orderStateToEntity(carOrder.getOrderState()))")
    CarOrderEntity toEntity(CarOrder carOrder);

    @Named("orderStateToEntity")
    default OrderStateEntity orderStateToEntity(OrderState orderState) {
        if (orderState instanceof DefaultOrderState) {
            return new DefaultOrderStateEntity(orderState.toString());
        }
        else if (orderState instanceof CustomOrderState) {
            return new CustomOrderStateEntity(orderState.toString()); // будем верить, что не упадет
        }
        else {
            throw new IllegalArgumentException("Unknown OrderState type: " + orderState.getClass());
        }
    }
}
