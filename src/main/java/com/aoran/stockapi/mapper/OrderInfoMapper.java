package com.aoran.stockapi.mapper;

import com.aoran.stockapi.dto.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * @author morty
 * @date 2022/7/25 20:02
 */
public interface OrderInfoMapper {


    int createOrderInfo( @Param("userId") Integer userId, @Param("carId") Integer carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    OrderInfo queryByOrderId(@Param("orderId") Integer orderId);

    int releaseStock(@Param("userId") Integer userId, @Param("carId") Integer carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int startOrder(@Param("orderId") Integer orderId);

    int endOrder(@Param("orderId") Integer orderId);
}
