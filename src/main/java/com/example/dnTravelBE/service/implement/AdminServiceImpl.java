package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.AdminLoginResDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.*;
import com.example.dnTravelBE.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final TourRepo tourRepo;
    private final StatusRepository statusRepository;
    private final PaymentRepo paymentRepo;
    private final RoleRepository roleRepository;
    private final RateTourRepo rateTourRepo;

    static List<Integer> chart(List<Account> accounts) {
        List<Integer> acc = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            acc.add(0);
        }
        for (Account account : accounts) {
            switch (account.getCreateAt().getMonth().getValue()) {
                case 1: {
                    acc.set(0, acc.get(0) + 1);
                    break;
                }
                case 2: {
                    acc.set(1, acc.get(1) + 1);
                    break;
                }
                case 3: {
                    acc.set(2, acc.get(2) + 1);
                    break;
                }
                case 4: {
                    acc.set(3, acc.get(3) + 1);
                    break;
                }
                case 5: {
                    acc.set(4, acc.get(4) + 1);
                    break;
                }
                case 6: {
                    acc.set(5, acc.get(5) + 1);
                    break;
                }
                case 7: {
                    acc.set(6, acc.get(6) + 1);
                    break;
                }
                case 8: {
                    acc.set(7, acc.get(7) + 1);
                    break;
                }
                case 9: {
                    acc.set(8, acc.get(8) + 1);
                    break;
                }
                case 10: {
                    acc.set(9, acc.get(9) + 1);
                    break;
                }
                case 11: {
                    acc.set(10, acc.get(10) + 1);
                    break;
                }
                case 12: {
                    acc.set(11, acc.get(11) + 1);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return acc;
    }

    @Override
    public AdminLoginResDto getInfoWhenLogin(String email) {
        Account account = accountRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("Not Found Account.", 1031));
        Admin admin = adminRepository.findByAccount_Id(account.getId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        AdminLoginResDto adminLoginResDto = new AdminLoginResDto();
        adminLoginResDto.setId(admin.getId());
        adminLoginResDto.setRole(admin.getAccount().getRole().getName());
        return adminLoginResDto;
    }

    @Override
    public ResponseEntity getTotalDashboard() {
        Status status = statusRepository.findByName(StatusEnum.ACCEPT).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1050));
        boolean test = false;
        Map<String, Object> objectMap = new HashMap<>();
        int totalCustomer = accountRepository.countAllByRoleName(AccountRole.ROLE_CUSTOMER);
        int totalProvider = accountRepository.countAllByRoleName(AccountRole.ROLE_PROVIDER);
        int totalTour = tourRepo.countAllByAdmin(status.getId(), test);
        Integer totalSale = paymentRepo.countAllByStatus("COMPLETE");
        objectMap.put("totalCustomer", totalCustomer);
        objectMap.put("totalProvider", totalProvider);
        objectMap.put("totalTour", totalTour);
        if (totalSale != null) {
            objectMap.put("totalSale", Double.valueOf(totalSale * 0.25));
        }else {
            objectMap.put("totalSale", 0.0);
        }

        return ResponseEntity.ok(ResponseDto.response(objectMap));
    }

    @Override
    public ResponseEntity getChartUserDashboard( int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        Optional<Role> roleCus = roleRepository.findByName(AccountRole.ROLE_CUSTOMER);
        Optional<Role> roleProvider = roleRepository.findByName(AccountRole.ROLE_PROVIDER);
        Map<String, Object> responce = new HashMap<>();
        List<Account> customers = accountRepository.findAllByRoleNameAndDAndCreateAt(roleCus.get().getId(), start, end);
        List<Account> providers = accountRepository.findAllByRoleNameAndDAndCreateAt(roleProvider.get().getId(), start, end);
        responce.put("customers", chart(customers));
        responce.put("providers", chart(providers));
        return ResponseEntity.ok(ResponseDto.response(responce));
    }

    @Override
    public ResponseEntity getChatPaymentDashboard( int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        List<Payment> payments = paymentRepo.findAllByStatusAndDAndCreateAt("COMPLETE", start, end);
        List<Double> acc = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            acc.add(0.0);
        }
        for (Payment payment : payments) {
            switch (payment.getCreateAt().getMonth().getValue()) {
                case 1: {
                    acc.set(0, acc.get(0) + Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 2: {
                    acc.set(1, acc.get(1) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 3: {
                    acc.set(2, acc.get(2) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 4: {
                    acc.set(3, acc.get(3) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 5: {
                    acc.set(4, acc.get(4) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 6: {
                    acc.set(5, acc.get(5) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 7: {
                    acc.set(6, acc.get(6) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 8: {
                    acc.set(7, acc.get(7) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 9: {
                    acc.set(8, acc.get(8) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 10: {
                    acc.set(9, acc.get(9) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 11: {
                    acc.set(10, acc.get(10) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                case 12: {
                    acc.set(11, acc.get(11) +  Double.valueOf(payment.getTotal()/4));
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return ResponseEntity.ok(ResponseDto.response(acc));
    }

    @Override
    public void deleteComment(Integer id) {
        Optional<RateTour> rateTour = rateTourRepo.findById(id);
        if (!rateTour.isEmpty()) {
            rateTour.get().setDelete(true);
        } else {
            throw new FailException("comment not found", 2031
            );
        }
        try {
            rateTourRepo.save(rateTour.get());
        } catch (Exception e) {
            throw new FailException("Cann't delete commnet", 2030);
        }
    }
}
