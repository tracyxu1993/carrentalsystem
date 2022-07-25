package com.aoran.stockapi.schedule;

import com.aoran.stockapi.config.Config;
import com.aoran.stockapi.dto.CarInfo;
import com.aoran.stockapi.dto.StockDetail;
import com.aoran.stockapi.po.StockInfo;
import com.aoran.stockapi.mapper.CarInfoMapper;
import com.aoran.stockapi.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morty
 * @date 2022/7/25 15:26
 */
@Component
@EnableScheduling
public class InitialStockTask {

    private static final Logger logger = LoggerFactory.getLogger(InitialStockTask.class);

    @Resource
    private StockMapper stockMapper;

    @Resource
    private CarInfoMapper carInfoMapper;

    /**
     * everyday initial car stock
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    private void initialStock(){
        LocalDate now = LocalDate.now();
        logger.info("{} initial stock task start", now);
        // query all available and initial stock info
        List<CarInfo> carInfos = carInfoMapper.queryAllCarInfo();

        for (CarInfo carInfo : carInfos){
            LocalDate endDate = stockMapper.queryMaxDate(carInfo.getCarId());
            // if never initial , today is first day
            if (endDate == null){
                endDate = now.minusDays(1);
            }
            LocalDate maxDate = now.plusDays(Config.PREPARED_STOCK_DAYS);
            List<StockDetail> stockInfos = new ArrayList<>();
            while (endDate.isBefore(maxDate)){
                endDate = endDate.plusDays(1);
                StockDetail info = new StockDetail();
                info.setCarModelId(carInfo.getCarModelId());
                info.setCarId(carInfo.getCarId());
                info.setStockDate(endDate);
                info.setCarModelName(carInfo.getCarModelName());
                stockInfos.add(info);
            }
            if (stockInfos.isEmpty()){
                continue;
            }
            // batch insert
            stockMapper.batchInsertStock(stockInfos);
        }
        logger.info("{} initial stock task end", now);
    }
}
