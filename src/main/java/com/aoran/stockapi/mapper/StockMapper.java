package com.aoran.stockapi.mapper;

import com.aoran.stockapi.dto.StockDetail;
import com.aoran.stockapi.po.ModelInfo;
import com.aoran.stockapi.po.StockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author morty
 * @date 2022/7/25 14:25
 */
@Mapper
@Repository
public interface StockMapper {

    List<ModelInfo> queryStockInfo(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("total") Integer total);

    LocalDate queryMaxDate(@Param("carId") Integer carId);

    int batchInsertStock(@Param("stockInfos") List<StockDetail> stockInfos);

    List<com.aoran.stockapi.po.StockDetail> queryStockDetail(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("carModelId") Integer carModelId, @Param("total") Integer total);

    int queryStockByCarId( @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("carId") Integer carId);

    int updateCarStockStatus(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("carId") Integer carId, @Param("userId") Integer userId);
}
