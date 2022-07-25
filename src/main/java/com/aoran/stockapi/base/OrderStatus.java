package com.aoran.stockapi.base;

/**
 * @author morty
 * @date 2022/7/25 22:10
 */
public enum OrderStatus {

    INITIAL(0, "initial"),
    START(1, "start"),
    END(2, "end")
    ;

    private Integer status;
    private String name;

    OrderStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
