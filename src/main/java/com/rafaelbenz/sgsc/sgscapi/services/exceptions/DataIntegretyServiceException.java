package com.rafaelbenz.sgsc.sgscapi.services.exceptions;

public class DataIntegretyServiceException extends RuntimeException{
    public DataIntegretyServiceException() {
    }

    public DataIntegretyServiceException(String message) {
        super(message);
    }

    public DataIntegretyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegretyServiceException(Throwable cause) {
        super(cause);
    }

    public DataIntegretyServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
