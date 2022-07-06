package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.TourMapper;
import com.example.dnTravelBE.mapper.temple.CurrentPeople;
import com.example.dnTravelBE.repository.*;
import com.example.dnTravelBE.request.*;
import com.example.dnTravelBE.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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

    private final PaymentRepo paymentRepo;

    private static int sizePage = 5;

    public int totalTourPages(int count) {
        if (count <= sizePage) {
            return 1;
        } else if (count % sizePage != 0) {
            return (int) Math.floor(count / sizePage) + 1;
        } else {
            return (int) Math.floor(count / sizePage);
        }
    }

    @Override
    public boolean createTour(TourDetailReq tourDetailReq) {
        Provider provider = providerRepository.findById(tourDetailReq.getProviderId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        Province province = provinceRepo.findById(tourDetailReq.getProvinceId()).
                orElseThrow(() -> new NotFoundException("Not Found province.", 1006));
        Category category = categoryRepo.findById(tourDetailReq.getCategoryTd()).
                orElseThrow(() -> new NotFoundException("Not Found category.", 1007));
        Status status = statusRepository.findByName(StatusEnum.WAITING).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1009));
        try {
            Tour tour = new Tour();
            tour.setName(tourDetailReq.getName());
            tour.setAdultPrice(tourDetailReq.getAdultPrice());
            tour.setChildPrice(tourDetailReq.getChildPrice());
            tour.setDescription(tourDetailReq.getDescription());
            tour.setSubDescription(tourDetailReq.getSubDescription());
            tour.setProvince(province);
            tour.setStatus(status);
            tour.setCategory(category);
            tour.setProvider(provider);
            tour.setNumberPeople(tourDetailReq.getNumberPeople());
            tour.setDelete(false);
            tour.setNumberDate(tourDetailReq.getNumberDate());
            Tour newTour = tourRepo.save(tour);
            for (int i = 0; i < tourDetailReq.getTourImage().size(); i++) {
                TourImage tourImage = new TourImage();
                tourImage.setLink(tourDetailReq.getTourImage().get(i));
                tourImage.setTour(newTour);
                if (i == 0) {
                    tourImage.setMain(true);
                } else {
                    tourImage.setMain(false);
                }
                tourImageRepo.save(tourImage);
            }
            for (int i = 0; i < tourDetailReq.getSchedules().size(); i++) {
                Schedule schedule = new Schedule();
                schedule.setTour(newTour);
                schedule.setDate(tourDetailReq.getSchedules().get(i));
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
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(0));
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
        int count = tourRepo.countAllByProviderAndStatusAndSearch(status.getId(), "%" + keyword + "%", providerId, isDelete);
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
        List<CurrentPeople> currentPeoples = new ArrayList<>();
        for (Schedule schedule : tour.getSchedules()){
            Integer currentPeople = paymentRepo.countByTourIdAndScheduleId(tour.getId(), schedule.getId());
            CurrentPeople currentPeople1 = new CurrentPeople();
            currentPeople1.setIdSchedule(schedule.getId());
            if( currentPeople == null) {
                currentPeople1.setCurrentPeople(0);
            } else {
                currentPeople1.setCurrentPeople(currentPeople);
            }
            currentPeoples.add(currentPeople1);
        }
        return TourMapper.toTourDetailDtoDetail(tour, currentPeoples);
    }

    @Override
    public TourDetailDto getTourDetailByIdOfProvider(Integer id) {
        Tour tour = tourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1015));
        return TourMapper.toTourDetailDtoProvider(tour);
    }

    @Override
    public void createRateTour(RateTourReq rateTourReq) {
        Customer customer = customerRepository.findById(rateTourReq.getCustomerId()).
                orElseThrow(() -> new NotFoundException("Not Found Customer.", 1015));
        Tour tour = tourRepo.findById(rateTourReq.getTourId()).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1016));
        Optional<RateTour> rateTourOld = rateTourRepo.findByTourIdAndCustomerId(tour.getId(), customer.getId());
        if(!rateTourOld.isEmpty()) {
            rateTourOld.get().setDelete(false);
            rateTourOld.get().setStar(rateTourReq.getStar());
            rateTourOld.get().setComment(rateTourReq.getComment());
        }
        RateTour rateTour = new RateTour();
        rateTour.setTour(tour);
        rateTour.setCreate_at(LocalDate.now());
        rateTour.setComment(rateTourReq.getComment());
        rateTour.setStar(rateTourReq.getStar());
        rateTour.setCustomer(customer);
        rateTour.setDelete(false);
        try {
            if(!rateTourOld.isEmpty()){
                rateTourRepo.save(rateTourOld.get());
            } else {
                rateTourRepo.save(rateTour);
            }
        } catch (Exception e) {
            throw new FailException("Can't create an rate tour", 1020);
        }
    }

    @Override
    public void editRateTour(Integer id, RateTourReq rateTourReq) {
        RateTour rateTour = rateTourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Rate Tour.", 1096));
        rateTour.setStar(rateTourReq.getStar());
        rateTour.setComment(rateTourReq.getComment());
        try {
            rateTourRepo.save(rateTour);
        }catch (Exception e) {
            throw new FailException("Can't update rate tour", 1020);
        }
    }

    @Override
    public void deleteRateTour(Integer id) {
        RateTour rateTour = rateTourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Rate Tour.", 1097));
        rateTour.setDelete(true);
        try {
            rateTourRepo.save(rateTour);
        }catch (Exception e) {
            throw new FailException("Can't delete rate tour", 1020);
        }
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
        List<Tour> tours = tourRepo.findAllByProviderIdAnDelete(providerId, "%" + keyword + "%", test, pageable);
        int count = tourRepo.countAllByDelete(providerId, "%" + keyword + "%", test);
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
    public Tour getTourDetailProvider(Integer id) {
        Tour tour = tourRepo.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1018));
        return tour;
    }

    @Override
    public RateTourDetail getRateTourDetailById(Integer tourId, Integer customerId) {
        Optional<RateTour> rateTour = rateTourRepo.findByTourIdAndCustomerId(tourId, customerId);
        RateTourDetail rateTourDetail = new RateTourDetail();
        if (!rateTour.isEmpty() && !rateTour.get().isDelete()) {
            rateTourDetail.setId(rateTour.get().getId());
            rateTourDetail.setTourName(rateTour.get().getTour().getName());
            rateTourDetail.setComment(rateTour.get().getComment());
            rateTourDetail.setStar(rateTour.get().getStar());
            rateTourDetail.setCreate_at(rateTour.get().getCreate_at());
        } else {
            throw new FailException("Rate tour is delete", 1020);
        }
        return rateTourDetail;
    }

    @Override
    public ResponseEntity getToursDashboard() {
        Optional<Province> province1 = provinceRepo.findById(1);
        Optional<Province> province2 = provinceRepo.findById(2);

        Optional<Status> status = statusRepository.findByName(StatusEnum.ACCEPT);
        boolean test = false;

        List<Tour> tour1 = tourRepo.findAllByProvinceAndStatusAndDelete(province1.get().getId(), status.get().getId(), test);
        List<Tour> tour2 = tourRepo.findAllByProvinceAndStatusAndDelete(province2.get().getId(), status.get().getId(), test);

        List<TourListDto> resTours1 = new ArrayList<>();
        List<TourListDto> resTours2 = new ArrayList<>();

        for (Tour tour : tour1) {
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(0));
            }

            resTours1.add(tourListDto);
        }

        for (Tour tour : tour2) {
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(0));
            }

            resTours2.add(tourListDto);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("start1" , province1.get());
        res.put("start2" , province2.get());
        res.put("tours1" , resTours1);
        res.put("tours2" , resTours2);
        return ResponseEntity.ok(ResponseDto.response(res));
    }

    @Override
    public ResponseTourListDto getAllTourByCategory(Integer categoryId) {
        boolean test = false;
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        Optional<Status> status = statusRepository.findByName(StatusEnum.ACCEPT);
        List<Tour> tours = tourRepo.findAllByCategoryIdAndStatusIdAndDelete(categoryId, test, status.get().getId());
        List<TourListDto> resTours1 = new ArrayList<>();
        for (Tour tour : tours) {
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(0));
            }

            resTours1.add(tourListDto);
        }
        responseTourListDto.setTours(resTours1);
        responseTourListDto.setPage(0);
        responseTourListDto.setTotal(1);
        return responseTourListDto;
    }

    @Override
    public ResponseTourListDto getAllTourByProvince(Integer provinceId) {
        boolean test = false;
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        Optional<Status> status = statusRepository.findByName(StatusEnum.ACCEPT);
        List<Tour> tours = tourRepo.findAllByProvinceIdAnAndStatusIdAndDelete(provinceId, test, status.get().getId());
        List<TourListDto> resTours1 = new ArrayList<>();
        for (Tour tour : tours) {
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour, Double.valueOf(0));
            }

            resTours1.add(tourListDto);
        }
        responseTourListDto.setTours(resTours1);
        responseTourListDto.setPage(0);
        responseTourListDto.setTotal(1);
        return responseTourListDto;
    }

    @Override
    public void editTour(Integer tourId, TourEditRes tourEditRes) {

        Tour tour = tourRepo.findById(tourId).
                orElseThrow(() -> new NotFoundException("Not Found Tour.", 1019));
        System.out.println(tourEditRes.getCategoryId());
        Category category = categoryRepo.findById(tourEditRes.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Not Found category.", 1020));
        Province province = provinceRepo.findById(tourEditRes.getProvinceId())
                .orElseThrow(() -> new NotFoundException("Not Found province.", 1021));
        Status status = statusRepository.findByName(StatusEnum.WAITING)
                 .orElseThrow(() -> new NotFoundException("Not Found status.", 1022));
        tour.setName(tourEditRes.getName());
        tour.setAdultPrice(tourEditRes.getAdultPrice());
        tour.setChildPrice(tourEditRes.getChildPrice());
        tour.setDescription(tourEditRes.getDescription());
        tour.setNumberDate(tourEditRes.getDateNumber());
        tour.setSubDescription(tourEditRes.getSubDescription());
        tour.setStatus(status);
        tour.setCategory(category);
        tour.setProvince(province);
        tour.setNumberPeople(tourEditRes.getNumberPeople());
        try{
            tourRepo.save(tour);
        }catch (Exception e) {
            throw new FailException("Can't update tour", 1023);
        }

        int i =0;
        for (TourImage tourImage : tour.getTourImages()){
            tourImage.setLink(tourEditRes.getTourImage().get(i));
            try {
                tourImageRepo.save(tourImage);
            }catch (Exception e) {
                throw new FailException("Can't update image tour", 1024);
            }
            i++;
        };

    }

    @Override
    public ResponseTourListDto searchHome(SearchHome searchHome) {
        List<Schedule> schedules = scheduleRepo.findAllByDate(searchHome.getDate());
        ResponseTourListDto responseTourListDto = new ResponseTourListDto();
        List<TourListDto> resTours1 = new ArrayList<>();
        for (Schedule schedule : schedules){
            if (schedule.getTour().getProvince().getId() == searchHome.getProvinceId()){
                int totalStar = 0;
                int totalReview = 0;
                for (RateTour rateTour : schedule.getTour().getRateTours()) {
                    totalStar += rateTour.getStar();
                    ++totalReview;
                }
                TourListDto tourListDto = new TourListDto();
                if (totalReview != 0) {
                    tourListDto = TourMapper.mapToTourListDto(schedule.getTour(), Double.valueOf(totalStar / totalReview));
                } else {
                    tourListDto = TourMapper.mapToTourListDto(schedule.getTour(), Double.valueOf(0));
                }

                resTours1.add(tourListDto);
            }
        }
        responseTourListDto.setTours(resTours1);
        responseTourListDto.setPage(0);
        responseTourListDto.setTotal(1);
        return responseTourListDto;
    }

    @Override
    public boolean isBookTour(CheckBookTour checkBookTour) {
        Optional<Payment> payment = paymentRepo.
                findByTourIdAndCustomerIdAndScheduleId(checkBookTour.getTourId(),checkBookTour.getCustomerId(), checkBookTour.getScheduleId());
        if (payment.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<Object> topPayment() {
        List<Integer> paymentIds = paymentRepo.getListTopPayment();
        List<TourListDto> tourListDtos = new ArrayList<>();
        for (Integer id : paymentIds) {
            Optional<Tour> tour = tourRepo.findById(id);
            int totalStar = 0;
            int totalReview = 0;
            for (RateTour rateTour : tour.get().getRateTours()) {
                totalStar += rateTour.getStar();
                ++totalReview;
            }
            TourListDto tourListDto = new TourListDto();
            if (totalReview != 0) {
                tourListDto = TourMapper.mapToTourListDto(tour.get(), Double.valueOf(totalStar / totalReview));
            } else {
                tourListDto = TourMapper.mapToTourListDto(tour.get(), Double.valueOf(0));
            }
            tourListDtos.add(tourListDto);
        }
        return ResponseEntity.ok(ResponseDto.response(tourListDtos));
    }
}
