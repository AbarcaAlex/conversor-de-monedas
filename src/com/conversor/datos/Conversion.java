package com.conversor.datos;

import com.conversor.excepciones.ErrorFromApiException;

public class Conversion{
    // atributos
    private String result;
    private String baseCode;
    private double conversionResult;
    private String targetCode;
    
    // constructor
    public Conversion(ExchangeRateApi exchangeRateApi) throws ErrorFromApiException{
        this.result = exchangeRateApi.result();
        if (exchangeRateApi.result().equals("error")) {
            throw new ErrorFromApiException("Los codigos de moneda introducidos no son validos!");
        }
        this.baseCode = exchangeRateApi.base_code();
        this.conversionResult = exchangeRateApi.conversion_result();
        this.targetCode = exchangeRateApi.target_code();
    }

    // getters
    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public double getConversionResult() {
        return conversionResult;
    }

    public String getTargetCode() {
        return targetCode;
    }

    
}
