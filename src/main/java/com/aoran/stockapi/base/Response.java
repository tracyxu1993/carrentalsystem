package com.aoran.stockapi.base;

/**
 * @author morty
 * @date 2022/7/25 09:48
 */
public class Response<T> {

    private Integer code;

    private T data;

    private String msg;


    public static final String SUCCESS = "success";

    public static final int SUCCESS_CODE= 0;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static Response failed(Integer code, String msg){
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static Response success(Object data){
        Response response = new Response();
        response.setCode(SUCCESS_CODE);
        response.setMsg(SUCCESS);
        response.setData(data);
        return response;
    }
}
