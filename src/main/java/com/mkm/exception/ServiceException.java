package com.mkm.exception;

public class ServiceException extends Exception {

    private String errorMessage;
    private Exception exception;
    private int errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(String errorMessage) {
       this.errorMessage = errorMessage;
    }
    public ServiceException(String errorMessage, Exception exception, int errorCode) {
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public ServiceException(String message, String errorMessage, Exception exception, int errorCode) {
        super(message);
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause, String errorMessage, Exception exception, int errorCode) {
        super(message, cause);
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public ServiceException(Throwable cause, String errorMessage, Exception exception, int errorCode) {
        super(cause);
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorMessage, Exception exception, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }



}
