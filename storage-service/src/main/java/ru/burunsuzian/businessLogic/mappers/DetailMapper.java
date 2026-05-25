package ru.burunsuzian.businessLogic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ru.burunsuzian.businessLogic.details.Detail;
import ru.burunsuzian.businessLogic.details.replaceable.Replaceable;
import ru.burunsuzian.data.jpa.domainEntities.DetailEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface DetailMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", expression = "java(new ru.burunsuzian.businessLogic.domain.valueObjects.Money(detailEntity.getPrice()))")
    @Mapping(target = "compatibleModels", source = "compatibleModels")
    @Mapping(target = "category", expression = "java(categoryToReplaceable(detailEntity.getCategory()))")
    Detail toDomain(DetailEntity detailEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", source = "price.money")
    @Mapping(target = "compatibleModels", source = "compatibleModels")
    @Mapping(target = "category", expression = "java(replaceableToEntity(detail))")
    DetailEntity toEntity(Detail detail);

    @Named("categoryToReplaceable")
    default Replaceable categoryToReplaceable(ReplaceableComponentType detailEntity) {
        var detail = detailEntity.toDomain();
        return detail;
    }

    default ReplaceableComponentType replaceableToEntity(Detail detail) {
        return detail.getCategory().toEntity(detail.getPrice(), detail.getCompatibleModels());
    }
}
