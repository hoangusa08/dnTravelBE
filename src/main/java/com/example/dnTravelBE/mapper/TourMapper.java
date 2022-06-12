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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<LocalDate> schedules = new ArrayList<>();
        for(Schedule schedule : tour.getSchedules()){
            schedules.add(schedule.getDate());
        }
        tourDetailDto.setSchedules(schedules);
        List<RateDto> rateDtos = new ArrayList<>();
        for (RateTour rateTour : tour.getRateTours()){
            RateDto rateDto = new RateDto();
            rateDto.setId(rateDto.getId());
            rateDto.setComment(rateTour.getComment());
            rateDto.setAvatar_source("");
            rateDto.setStar(rateTour.getStar());
            rateDto.setCreate_at(rateTour.getCreate_at());
            rateDtos.add(rateDto);
        }
        tourDetailDto.setRateTours(rateDtos);
        return tourDetailDto;
    }

    public  static TourListDto  mapToTourListDto (Tour tour, Integer star){
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
