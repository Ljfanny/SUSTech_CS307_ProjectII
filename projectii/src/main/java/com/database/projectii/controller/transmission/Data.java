package com.database.projectii.controller.transmission;


public class Data {
    private Object data;
    private String message;

    @Override
    public String toString() {
        return "Data{" +
            "data=" + data +
            ", message='" + message + '\'' +
            '}';
    }
    public Data(){}

    public Data(Object data, String message){
        this.data = data;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
