package com.conversor.excepciones;

public class ErrorFromApiException extends Exception{
    private String mensaje;

    public ErrorFromApiException(String mensaje){
        this.mensaje=mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }

    
}
