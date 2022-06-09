package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ResponseTourListDto;
import com.example.dnTravelBE.dto.TourDetailDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.dto.TourResDto;
import com.example.dnTravelBE.request.RateTourReq;
import io.swagger.models.auth.In;

import java.util.List;

public interface TourService {

    boolean createTour(TourDto tourDto);

    ResponseTourListDto getAllTour(StatusEnum statusEnum, Integer page, String keyword);

    ResponseTourListDto getAllTourProvider(Integer providerId, StatusEnum statusEnum, Integer page, String keyword);

    TourDetailDto getTourDetailById(Integer id);

    void createRateTour(RateTourReq rateTourReq);

    void editTour();

    void changeStatusTour(Integer tourId, StatusEnum statusEnum);

    void setStatusTour(Integer id, boolean status);

}
