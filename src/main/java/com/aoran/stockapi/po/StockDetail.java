package com.aoran.stockapi.po;

/**
 * @author morty
 * @date 2022/7/25 22:54
 */
public class StockDetail {

    private String carModelName;

    private Integer carModelId;

    private Integer carId;

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public Integer getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Integer carModelId) {
        this.carModelId = carModelId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
