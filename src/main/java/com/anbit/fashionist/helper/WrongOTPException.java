package com.anbit.fashionist.helper;

public class WrongOTPException extends Throwable{

    public WrongOTPException(String message) {
        super(message);
    }

}