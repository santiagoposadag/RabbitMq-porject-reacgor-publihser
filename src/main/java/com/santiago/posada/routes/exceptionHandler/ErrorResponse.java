package com.santiago.posada.routes.exceptionHandler;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
