package com.nova.aathif.taxibookingapi.common;

public class AccessDeniedExcption extends RuntimeException{
    public AccessDeniedExcption(String message){
        super(message);
    }
}
