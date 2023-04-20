package com.carparking.application.exception;
import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private String message;

    private int status;

    private Object data;

    public ErrorDetails(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.status = httpStatus.value();
    }

    public ErrorDetails(String message, HttpStatus httpStatus, String data) {
        super();
        this.message = message;
        this.status = httpStatus.value();
        this.data = data;
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
