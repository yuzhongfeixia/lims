package com.gpersist.entity;

public class ServiceReturn {
    private boolean success;

    private Object data;

    private String message;

    public ServiceReturn() {
        this.success = true;
        this.data = null;
        this.message = "";
    }

    public ServiceReturn(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
