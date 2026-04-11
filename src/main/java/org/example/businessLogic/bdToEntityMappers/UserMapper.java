package org.example.businessLogic.bdToEntityMappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.example.businessLogic.domain.User;
import org.example.businessLogic.domain.UserType;
import org.example.data.jpa.domainEntities.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "userType", expression = "java(UserType.fromString(userEntity.getUserType()))")
    User toDomain(UserEntity userEntity);

    default UserType mapUserType(String value) {
        return UserType.fromString(value);
    }
}
