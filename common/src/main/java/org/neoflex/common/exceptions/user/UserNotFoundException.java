package org.neoflex.common.exceptions.user;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User not found!");
    }
}
