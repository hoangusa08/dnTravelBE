package com.example.dnTravelBE.service;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.Tour;
import com.example.dnTravelBE.request.*;
import org.springframework.http.ResponseEntity;

public interface TourService {

    boolean createTour(TourDetailReq tourDetailReq);

    ResponseTourListDto getAllTour(StatusEnum statusEnum, Integer page, String keyword);

    ResponseTourListDto getAllTourProvider(Integer providerId, StatusEnum statusEnum, Integer page, String keyword, boolean isDelete);

    TourDetailDto getTourDetailById(Integer id);

    TourDetailDto getTourDetailByIdOfProvider(Integer id);

    void createRateTour(RateTourReq rateTourReq);

    void editRateTour( Integer id, RateTourReq rateTourReq);

    void deleteRateTour( Integer id);

    void changeStatusTour(Integer tourId, StatusEnum statusEnum);

    void setStatusTour(Integer id, boolean status);

    ResponseTourListDto getAllTourDelete ( Integer providerId, Integer page, String keyWord);

    Tour getTourDetailProvider( Integer id);

    RateTourDetail getRateTourDetailById ( Integer tourId, Integer customerId);

    ResponseEntity getToursDashboard();

    ResponseTourListDto getAllTourByCategory(Integer categoryId);

    ResponseTourListDto getAllTourByProvince(Integer provinceId);

    void editTour(Integer tourId, TourEditRes tourEditRes);

    ResponseTourListDto searchHome(SearchHome searchHome);

    boolean isBookTour(CheckBookTour checkBookTour);

    ResponseEntity<Object> topPayment();
}
