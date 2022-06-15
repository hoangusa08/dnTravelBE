package com.example.dnTravelBE.mapper;

import com.example.dnTravelBE.dto.RateDto;
import com.example.dnTravelBE.dto.TourDetailDto;
import com.example.dnTravelBE.dto.TourListDto;
import com.example.dnTravelBE.entity.RateTour;
import com.example.dnTravelBE.entity.Schedule;
import com.example.dnTravelBE.entity.Tour;
import com.example.dnTravelBE.entity.TourImage;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.*;

public class TourMapper {

    public static TourDetailDto toTourDetailDto(Tour tour) {
        TourDetailDto tourDetailDto = new TourDetailDto();
        tourDetailDto.setId(tour.getId());
        tourDetailDto.setName(tour.getName());
        tourDetailDto.setAdultPrice(tour.getAdultPrice());
        tourDetailDto.setChildPrice(tour.getChildPrice());
        tourDetailDto.setDescription(tour.getDescription());
        List<String> tourImgs = new ArrayList<>();
        for (TourImage tourImage: tour.getTourImages()){
            tourImgs.add(tourImage.getLink());
        }
        tourDetailDto.setTourImage(tourImgs);
        List<Object> schedules = new ArrayList<>();
        for(Schedule schedule : tour.getSchedules()){
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("id", schedule.getId());
            objectMap.put("date" , schedule.getDate());
            schedules.add(objectMap);
        }
        tourDetailDto.setSchedules(schedules);
        List<RateDto> rateDtos = new ArrayList<>();
        int totalStar = 0;
        int totalReview = 0;
        for (RateTour rateTour : tour.getRateTours()){
            totalStar += rateTour.getStar();
            ++totalReview;
            RateDto rateDto = new RateDto();
            rateDto.setId(rateDto.getId());
            rateDto.setComment(rateTour.getComment());
            rateDto.setAvatar_source("");
            rateDto.setStar(rateTour.getStar());
            rateDto.setCreate_at(rateTour.getCreate_at());
            rateDto.setUser_full_name(rateTour.getCustomer().getFullName());
            rateDtos.add(rateDto);
        }
        tourDetailDto.setRateTours(rateDtos);
        if (totalReview == 0) {
            tourDetailDto.setTotalStar(0.0);
        } else {
            tourDetailDto.setTotalStar(Double.valueOf(totalStar/totalReview));
        }
        return tourDetailDto;
    }

    public  static TourListDto  mapToTourListDto (Tour tour, Double star){
        TourListDto tourListDto = new TourListDto();
        tourListDto.setId(tour.getId());
        tourListDto.setName(tour.getName());
        tourListDto.setAdultPrice(tour.getAdultPrice());
        tourListDto.setChildPrice(tour.getChildPrice());
        tourListDto.setDescription(tour.getDescription());
        tourListDto.setCategory(tour.getCategory().getName());
        tourListDto.setProvider(tour.getProvider().getNameCompany());
        tourListDto.setProvince(tour.getProvince().getName());
        tourListDto.setTourImage(tour.getTourImages().iterator().next().getLink());
        tourListDto.setDetele(tour.isDelete());
        tourListDto.setStar(star);
        tourListDto.setStatus(tour.getStatus().getName());
        tourListDto.setSubDescription(tour.getSubDescription());
        return tourListDto;
    }
}
