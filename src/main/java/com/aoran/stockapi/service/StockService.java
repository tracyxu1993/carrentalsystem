package com.aoran.stockapi.service;

import com.aoran.stockapi.po.ModelInfo;

import java.time.LocalDate;
import java.util.List;

/**
 * @author morty
 * @LocalDate 2022/7/25 09:35
 */
public interface StockService {


    List<ModelInfo> queryStockInfo(LocalDate startLocalDate, LocalDate endLocalDate);

    boolean releaseStock(Integer orderId);

    boolean orderStock(LocalDate startLocalDate, LocalDate endLocalDate, Integer carId, Integer userId);

    List<com.aoran.stockapi.po.StockDetail> stockDetail(LocalDate startDate, LocalDate endDate, Integer carModelId);

    boolean startOrder(Integer orderId);
}
