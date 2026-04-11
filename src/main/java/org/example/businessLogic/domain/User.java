package org.example.businessLogic.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public final class User {
    private UserType userType;
    private UUID userID;

    public User(UserType someType){
        userType = someType;
        userID = UUID.randomUUID();
    }
}
