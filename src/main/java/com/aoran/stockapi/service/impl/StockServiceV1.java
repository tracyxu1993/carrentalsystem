package com.aoran.stockapi.service.impl;

import com.aoran.stockapi.dto.OrderInfo;
import com.aoran.stockapi.dto.StockDetail;
import com.aoran.stockapi.mapper.OrderInfoMapper;
import com.aoran.stockapi.mapper.StockMapper;
import com.aoran.stockapi.po.ModelInfo;
import com.aoran.stockapi.po.StockInfo;
import com.aoran.stockapi.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author morty
 * @LocalDate 2022/7/25 09:59
 */
@Service
public class StockServiceV1 implements StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceV1.class);

    @Resource
    private StockMapper stockMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<ModelInfo> queryStockInfo(LocalDate startDate, LocalDate endDate) {
        int total = (int) ( endDate.toEpochDay() - startDate.toEpochDay() + 1);
        List<ModelInfo> stockInfos = stockMapper.queryStockInfo(startDate, endDate, total);
        return stockInfos;
    }


    @Override
    public boolean releaseStock(Integer orderId) {
        OrderInfo orderInfo = orderInfoMapper.queryByOrderId(orderId);
        if (orderInfo == null){
            // order info not exist
            return false;
        }
        LocalDate now = LocalDate.now();
        LocalDate start = now.plusDays(1);
        if (now.isBefore(orderInfo.getEndDate())){
            // back car before end update stock status;
            if (start.isBefore(orderInfo.getStartDate())){
                start = orderInfo.getStartDate();
            }
            orderInfoMapper.releaseStock(orderInfo.getUserId(), orderInfo.getCarId(), start, orderInfo.getEndDate());
        }
        orderInfoMapper.endOrder(orderId);
        return true;
    }


    @Override
    public boolean orderStock(LocalDate startDate, LocalDate endDate, Integer carId, Integer userId) {
        int days = stockMapper.queryStockByCarId(startDate, endDate, carId);
        int total = (int) ( endDate.toEpochDay() - startDate.toEpochDay() + 1);
        if (total > days){
            // stock day not enough
            return false;
        }
        //update stock status;
        int updateCount = stockMapper.updateCarStockStatus(startDate, endDate, carId, userId);
        //make order
        orderInfoMapper.createOrderInfo(userId, carId, startDate, endDate);

        return updateCount == total;
    }

    @Override
    public boolean startOrder(Integer orderId) {
        OrderInfo orderInfo = orderInfoMapper.queryByOrderId(orderId);
        if (orderInfo == null){
            return false;
        }
        LocalDate now = LocalDate.now();
        if (now.isBefore(orderInfo.getStartDate())){
            // order not start
            return false;
        }
        orderInfoMapper.startOrder(orderId);
        return false;
    }

    @Override
    public List<com.aoran.stockapi.po.StockDetail> stockDetail(LocalDate startDate, LocalDate endDate, Integer carModelId) {
        // need days
        int total = (int) ( endDate.toEpochDay() - startDate.toEpochDay() + 1);
        return stockMapper.queryStockDetail(startDate, endDate, carModelId, total);
    }
}
