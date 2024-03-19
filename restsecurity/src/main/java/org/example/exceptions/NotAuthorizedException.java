package org.example.exceptions;

public class NotAuthorizedException extends Throwable{
    public NotAuthorizedException(int code, String msg){
        super(msg+", "+code);
    }
}
