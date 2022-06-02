package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.TourDetailDto;
import com.example.dnTravelBE.dto.TourDto;
import com.example.dnTravelBE.dto.TourResDto;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.TourMapper;
import com.example.dnTravelBE.repository.*;
import com.example.dnTravelBE.request.RateTourReq;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TourServiceImpl implements TourService {

    private final ProviderRepository providerRepository;
    private final ProvinceRepo provinceRepo;
    private final TourRepo tourRepo;
    private final StatusRepository statusRepository;
    private final CategoryRepo categoryRepo;
    private final TourImageRepo tourImageRepo;
    private final ScheduleRepo scheduleRepo;
    private final RateTourRepo  rateTourRepo;
    private final CustomerRepository customerRepository;

    public int totalTourPages(StatusEnum statusEnum) {
        int count = tourRepo.countAllByStatus(statusEnum);
        if(count <= 10) {
            return 1;
        }
        else if (count % 10 != 0) {
            return (int) Math.floor(count / 10) + 1;
        }
        else  {
            return (int) Math.floor(count / 10);
        }
    }

    @Override
    public boolean createTour(TourDto tourDto) {
        Provider provider = providerRepository.findById(tourDto.getProviderId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        Province province = provinceRepo.findById(tourDto.getProvinceId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1006));
        Category category = categoryRepo.findById(tourDto.getCategoryTd()).
                orElseThrow(() -> new NotFoundException("Not Found category.", 1007));
        Status status = statusRepository.findByName(StatusEnum.WAITING).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1009));
        try {
            Tour tour = new Tour();
            tour.setName(tourDto.getName());
            tour.setAdultPrice(tourDto.getAdultPrice());
            tour.setChildPrice(tourDto.getChildPrice());
            tour.setDescription(tourDto.getDescription());
            tour.setProvince(province);
            tour.setStatus(status);
            tour.setCategory(category);
            tour.setProvider(provider);
            tour.setDelete(false);
            Tour newTour = tourRepo.save(tour);
            for (int i = 0; i< tourDto.getTourImage().size(); i++){
                TourImage tourImage = new TourImage();
                tourImage.setLink(tourDto.getTourImage().get(i));
                tourImage.setTour(newTour);
                if(i == 0){
                    tourImage.setMain(true);
                }else {
                    tourImage.setMain(false);
                }
                tourImageRepo.save(tourImage);
            }
            for (int i = 0; i< tourDto.getSchedules().size(); i++){
                 Schedule schedule = new Schedule();
                 schedule.setTour(newTour);
                 schedule.setDate(tourDto.getSchedules().get(i));
                 scheduleRepo.save(schedule);
            }
        }catch (Exception e) {
            throw new FailException("Can't create an tour", 1008);
        }
        return true;
    }

    @Override
    public List<TourResDto> getAllTour(Integer page, String keyword) {
        Pageable pageable = (Pageable) PageRequest.of(page, 10);
        List<TourResDto> tourResDtos = new ArrayList<>();
        return null;
    }

    @Override
    public TourDetailDto getTourDetailById(Integer id) {
        Tour tour = tourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1014));

        return TourMapper.toTourDetailDto(tour);
    }

    @Override
    public void createRateTour(RateTourReq rateTourReq) {
        Customer customer = customerRepository.findById(rateTourReq.getCustomerId()).
                orElseThrow(() -> new NotFoundException("Not Found Customer.", 1015));
        Tour tour = tourRepo.findById(rateTourReq.getTourId()).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1016));
        RateTour rateTour = new RateTour();
        rateTour.setTour(tour);
        rateTour.setCreate_at(rateTourReq.getCreateAt());
        rateTour.setComment(rateTourReq.getComment());
        rateTour.setStar(rateTourReq.getStar());
        rateTour.setCustomer(customer);
        try {
            rateTourRepo.save(rateTour);
        }catch (Exception e) {
            throw new FailException("Can't create an rate tour", 1020);
        }
    }

    @Override
    public void editTour() {

    }

    @Override
    public void changeStatusTour(Integer tourId, StatusEnum statusEnum) {
        Tour tour = tourRepo.findById(tourId).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1024));
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1025));
        tour.setStatus(status);
    }
}
