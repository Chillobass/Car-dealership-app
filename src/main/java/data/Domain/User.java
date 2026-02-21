package data.Domain;

import java.util.UUID;

public final class User {
    private UserType userType;
    private UUID userID;

    public User(UserType someType){
        userType = someType;
        userID = UUID.randomUUID();
    }
}
