package com.aoran.stockapi.controller;

import com.aoran.stockapi.base.Response;
import com.aoran.stockapi.po.ModelInfo;
import com.aoran.stockapi.po.StockInfo;
import com.aoran.stockapi.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author morty
 * @date 2022/7/25 09:46
 */
@RestController
@RequestMapping("/stock/api")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    private static ReentrantLock lock = new ReentrantLock();

    @Resource
    private StockService stockService;

    /**
     * query stock
     * @return
     */
    @GetMapping("/queryStock")
    public Response queryStockInfo(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        try {
            List<ModelInfo> stockInfos = stockService.queryStockInfo(startDate, endDate);
            return Response.success(stockInfos);
        }catch (Exception e){
            logger.error("query stock failed", e);
            return Response.failed(-1, "query stock failed");
        }
    }

    @GetMapping("/stockDetail")
    public Response stockDetail(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @RequestParam(name = "carModelId") Integer carModelId){
        try {
            List<com.aoran.stockapi.po.StockDetail> page = stockService.stockDetail(startDate, endDate, carModelId);
            return Response.success(page);
        }catch (Exception e){
            logger.error("query stock failed", e);
            return Response.failed(-1, "query stock failed");
        }
    }

    /**
     * order stock
     * @param startDate
     * @param endDate
     * @param carId
     * @return
     */
    @PostMapping("/orderStock")
    public Response orderStock(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @RequestParam(name = "carId") Integer carId, @RequestParam(name = "userId") Integer userId){
        try{
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                boolean result = stockService.orderStock(startDate, endDate, carId, userId);
                if (!result){
                    return Response.failed(-1, "order stock failed");
                }
                return Response.success(result);
            }else {
                return Response.failed(-1, "order stock failed");
            }
        }catch (Exception e){
            logger.error("order stock failed", e);
            return Response.failed(-1, "order stock failed");
        }
    }

    @RequestMapping("/startOrder")
    public Response startOrder(@RequestParam(name = "orderId") Integer orderId){
        try{
            boolean result = stockService.startOrder(orderId);
            if (!result){
                return Response.failed( -1, "order start failed");
            }
            return Response.success(result);
        }catch (Exception e){
            logger.error("order start failed", e);
            return Response.failed(-1, "order start failed");
        }

    }

    /**
     * release stock
     * @param orderId
     * @return
     */
    @PostMapping("/releaseStock")
    public Response<Boolean> releaseStock(@RequestParam(name = "orderId") Integer orderId){
        try {
            boolean result = stockService.releaseStock(orderId);
            return Response.success(result);
        }catch (Exception e){
            logger.error("release stock failed", e);
            return Response.failed(-1, "release stock failed, please try again");
        }
    }
}
