package com.aoran.stockapi.po;

/**
 * @author morty
 * @date 2022/7/25 23:09
 */
public class ModelInfo {

    private Integer modelId;

    private String modelName;

    private Integer stockCount;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}
