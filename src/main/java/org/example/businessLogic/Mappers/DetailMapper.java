package org.example.businessLogic.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.example.businessLogic.details.Detail;
import org.example.businessLogic.details.replaceable.Replaceable;
import org.example.data.jpa.domainEntities.DetailEntity;
import org.example.data.jpa.replaceableEntities.ReplaceableComponentType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface DetailMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", expression = "java(new org.example.businessLogic.domain.valueObjects.Money(detailEntity.getPrice()))")
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
