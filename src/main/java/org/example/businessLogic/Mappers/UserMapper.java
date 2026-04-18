package org.example.businessLogic.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.example.businessLogic.domain.User;
import org.example.businessLogic.domain.UserType;
import org.example.data.jpa.domainEntities.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "userType", expression = "java(UserType.fromString(userEntity.getUserType()))")
    @Mapping(target = "userId", source = "id")
    User toDomain(UserEntity userEntity);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "userType", expression = "java(userTypeTostring(user))")
    UserEntity toEntity(User user);

    default UserType mapUserType(String value) {
        return UserType.fromString(value);
    }

    default String userTypeTostring(User user) {
        return user.getUserType().toString();
    }
}
