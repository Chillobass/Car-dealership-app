package ru.burunsuzian.businessLogic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.burunsuzian.application.responses.WarehouseAcceptanceResponse;
import ru.burunsuzian.businessLogic.domain.WarehouseAcceptance;
import ru.burunsuzian.data.jpa.domainEntities.WarehouseAcceptanceEntity;
import ru.burunsuzian.data.jpa.replaceableEntities.ReplaceableComponentType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WarehouseAcceptanceMapper {
    @Mapping(target = "requiredComponentsIds", expression = "java(mapComponents(entity.getRequiredComponents()))")
    WarehouseAcceptance toDomain(WarehouseAcceptanceEntity entity);

    WarehouseAcceptanceResponse toResponse(WarehouseAcceptance domain);

    default Set<UUID> mapComponents(Set<ReplaceableComponentType> components){
        if (components == null) return new HashSet<>();

        return components.stream()
                .map(ReplaceableComponentType::getId)
                .collect(Collectors.toSet());
    }

    void updateEntity(WarehouseAcceptance domain, @MappingTarget WarehouseAcceptanceEntity entity);
}
