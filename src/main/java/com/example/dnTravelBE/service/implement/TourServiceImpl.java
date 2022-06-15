package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.TourMapper;
import com.example.dnTravelBE.repository.*;
import com.example.dnTravelBE.request.RateTourReq;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RateTourRepo rateTourRepo;
    private final CustomerRepository customerRepository;

    private static int sizePage = 5;

    public int totalTourPages(int count) {
//        Status status = statusRepository.findByName(statusEnum).
//                orElseThrow(() -> new NotFoundException("Not Found status.", 1112));
//        int count = tourRepo.countAllByStatus(status);
        if (count <= sizePage) {
            return 1;
        } else if (count % sizePage != 0) {
            return (int) Math.floor(count / sizePage) + 1;
        } else {
            return (int) Math.floor(count / sizePage);
        }
    }

    @Override
    public boolean createTour(TourDto tourDto) {
        Provider provider = providerRepository.findById(tourDto.getProviderId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        Province province = provinceRepo.findById(tourDto.getProvinceId()).
                orElseThrow(() -> new NotFoundException("Not Found province.", 1006));
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
            tour.setSubDescription(tourDto.getSubDescription());
            tour.setProvince(province);
            tour.setStatus(status);
            tour.setCategory(category);
            tour.setProvider(provider);
            tour.setDelete(false);
            tour.setNumberDate(tourDto.getNumberDate());
            Tour newTour = tourRepo.save(tour);
            for (int i = 0; i < tourDto.getTourImage().size(); i++) {
                TourImage tourImage = new TourImage();
                tourImage.setLink(tourDto.getTourImage().get(i));
                tourImage.setTour(newTour);
                if (i == 0) {
                    tourImage.setMain(true);
                } else {
                    tourImage.setMain(false);
                }
                tourImageRepo.save(tourImage);
            }
            for (int i = 0; i < tourDto.getSchedules().size(); i++) {
                Schedule schedule = new Schedule();
                schedule.setTour(newTour);
                schedule.setDate(tourDto.getSchedules().get(i));
                scheduleRepo.save(schedule);
            }
        } catch (Exception e) {
            throw new FailException("Can't create an tour", 1008);
        }
        return true;
    }

    @Override
    public ResponseTourListDto getAllTour(StatusEnum statusEnum, Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1111));
        List<Tour> tours = tourRepo.findAllByStatusId(status.getId(), "%" + keyword + "%", pageable);
        int count = tourRepo.countAllByStatusAndSearch(status.getId(), "%" + keyword + "%");
        Integer total = (Integer) totalTourPages(count);
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (Tour tour : tours) {
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0){
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf (totalStar/totalReview) );
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf (0) );
            }

            tourListDtos.add(tourListDto);
        }
        responseTourListDto.setTours(tourListDtos);
        responseTourListDto.setTotal(total);
        responseTourListDto.setPage(page);
        return responseTourListDto;
    }

    @Override
    public ResponseTourListDto getAllTourProvider(Integer providerId, StatusEnum statusEnum, Integer page, String keyword, boolean isDelete) {
        Pageable pageable = PageRequest.of(page, sizePage);
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1111));
        List<Tour> tours = tourRepo.findAllByStatusIdAndProviderId(status.getId(), providerId, "%" + keyword + "%", isDelete, pageable);
        int count = tourRepo.countAllByProviderAndStatusAndSearch(status.getId(), "%" + keyword + "%", providerId);
        Integer total = (Integer) totalTourPages(count);
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (Tour tour : tours) {
            TourListDto tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(5));
            tourListDtos.add(tourListDto);
        }
        responseTourListDto.setTours(tourListDtos);
        responseTourListDto.setTotal(total);
        responseTourListDto.setPage(page);
        return responseTourListDto;
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
        } catch (Exception e) {
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

    @Override
    public void setStatusTour(Integer id, boolean status) {
        Tour tour = tourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1017));
        tour.setDelete(status);
        try {
            tourRepo.save(tour);
        } catch (Exception e) {
            throw new FailException("Can't update isDelete tour", 1020);
        }
    }

    @Override
    public ResponseTourListDto getAllTourDelete(Integer providerId, Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        boolean test = true;
        List<Tour> tours = tourRepo.findAllByProviderIdAnDelete(providerId,"%" + keyword + "%", test ,pageable);
        int count = tourRepo.countAllByDelete(providerId, "%" + keyword + "%" , test);
        Integer total = (Integer) totalTourPages(count);
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (Tour tour : tours) {
            TourListDto tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(5));
            tourListDtos.add(tourListDto);
        }
        responseTourListDto.setTours(tourListDtos);
        responseTourListDto.setTotal(total);
        responseTourListDto.setPage(page);
        return responseTourListDto;
    }
}
