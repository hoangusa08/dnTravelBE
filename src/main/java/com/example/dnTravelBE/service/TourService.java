package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.TourDetailDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.dto.TourResDto;

import java.util.List;

public interface TourService {

    boolean createTour(TourDto tourDto);

    List<TourResDto> getAllTour(Integer page, String keywork);

    TourDetailDto getTourDetailById(Integer id)      ;

}
