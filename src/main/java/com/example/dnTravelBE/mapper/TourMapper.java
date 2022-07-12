package com.example.dnTravelBE.mapper;

import com.example.dnTravelBE.dto.RateDto;
import com.example.dnTravelBE.dto.TourDetailDto;
import com.example.dnTravelBE.dto.TourListDto;
import com.example.dnTravelBE.entity.RateTour;
import com.example.dnTravelBE.entity.Schedule;
import com.example.dnTravelBE.entity.Tour;
import com.example.dnTravelBE.entity.TourImage;
import com.example.dnTravelBE.mapper.temple.CurrentPeople;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
public class TourMapper {

    public static TourDetailDto toTourDetailDtoProvider(Tour tour) {
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
        tourDetailDto.setSubDescription(tour.getSubDescription());
        Map<String, Object> category = new HashMap<>();
        Map<String, Object> startLocation = new HashMap<>();
        tourDetailDto.setCategory(tour.getCategory());
        category.put("id", tour.getCategory().getId());
        category.put("name", tour.getCategory().getName());
        tourDetailDto.setCategory(category);
        startLocation.put("id", tour.getProvince().getId());
        startLocation.put("name", tour.getProvince().getName());
        tourDetailDto.setLocationStart(startLocation);
        tourDetailDto.setDateNumber(tour.getNumberDate());
        tourDetailDto.setNumberPeople(tour.getNumberPeople());
        tourDetailDto.setCompany(tour.getProvider().getNameCompany());
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

    public static TourDetailDto toTourDetailDtoDetail(Tour tour, List<CurrentPeople> currentPeoples) {
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
        LocalDate localDate = LocalDate.now();
        localDate.plusDays(4);
        int i = 0;
        for(Schedule schedule : tour.getSchedules()){
            if( localDate.isBefore(schedule.getDate())){
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id", schedule.getId());
                objectMap.put("date" , schedule.getDate());
                for (CurrentPeople currentPeople : currentPeoples){
                    if(currentPeople.getIdSchedule() == schedule.getId()){
                        objectMap.put("currentPeople", currentPeople.getCurrentPeople());
                    }
                }
                schedules.add(objectMap);
            }
        }
        tourDetailDto.setSchedules(schedules);
        List<RateDto> rateDtos = new ArrayList<>();
        int totalStar = 0;
        int totalReview = 0;
        for (RateTour rateTour : tour.getRateTours()){
            if(!rateTour.isDelete()){
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
        }
        tourDetailDto.setRateTours(rateDtos);
        if (totalReview == 0) {
            tourDetailDto.setTotalStar(0.0);
        } else {
            tourDetailDto.setTotalStar(Double.valueOf(totalStar/totalReview));
        }
        tourDetailDto.setSubDescription(tour.getSubDescription());
        Map<String, Object> category = new HashMap<>();
        Map<String, Object> startLocation = new HashMap<>();
        tourDetailDto.setCategory(tour.getCategory());
        category.put("id", tour.getCategory().getId());
        category.put("name", tour.getCategory().getName());
        tourDetailDto.setCategory(category);
        startLocation.put("id", tour.getProvince().getId());
        startLocation.put("name", tour.getProvince().getName());
        tourDetailDto.setLocationStart(startLocation);
        tourDetailDto.setDateNumber(tour.getNumberDate());
        tourDetailDto.setNumberPeople(tour.getNumberPeople());
        tourDetailDto.setCompany(tour.getProvider().getNameCompany());
        return tourDetailDto;
    }
}
