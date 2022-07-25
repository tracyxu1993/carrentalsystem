package com.aoran.stockapi.mapper;

import com.aoran.stockapi.dto.CarInfo;

import java.util.List;

/**
 * @author morty
 * @date 2022/7/25 17:04
 */
public interface CarInfoMapper {


    List<CarInfo> queryAllCarInfo();
}
