package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.ProviderMapper;
import com.example.dnTravelBE.mapper.TourMapper;
import com.example.dnTravelBE.repository.*;
import com.example.dnTravelBE.request.ProviderReq;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final StatusRepository statusRepository;
    private final ProviderRepository providerRepository;
    private final AccountRepository accountRepository;
    private final BankRepo bankRepo;
    private final PaymentRepo paymentRepo;
    private final TourRepo tourRepo;
    private final AdminRepository adminRepository;

    private static int sizePage = 10;

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
    public void createProvider(RegisterProviderDto registerProviderDto, Account account) {

        Status status = statusRepository.findByName(StatusEnum.WAITING)
                .orElseThrow(() -> new NotFoundException("Status input invalid" , 1032));
        Bank bank = bankRepo.findBankById(registerProviderDto.getBankId())
                .orElseThrow(() -> new NotFoundException("Bank input invalid" , 1033));
        try {
            Provider provider = new Provider();
            provider.setNameCompany(registerProviderDto.getNameCompany());
            provider.setOwner(registerProviderDto.getOwner());
            provider.setPhoneNumber(registerProviderDto.getPhoneNumber());
            provider.setAddress(registerProviderDto.getAddress());
            provider.setBankNumber(registerProviderDto.getBankNumber());
            provider.setStatus(status);
            provider.setAccount(account);
            provider.setBank(bank);
            providerRepository.save(provider);
        }catch (Exception e) {
            throw new FailException("Cann't create provider", 2002);
        }
    }

    @Override
    public ProviderResLoginDto getProviderWhenLogin(String email) {
        Account account = accountRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("Not Found Account.", 1023));
        Provider provider = providerRepository.findByAccount_Id(account.getId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        if (provider.getStatus().getName() == StatusEnum.WAITING ){
            throw new FailException("Tài khoản của bạn chưa được chấp nhận", 2004);
        }
        if (provider.getStatus().getName() == StatusEnum.REFUSE ){
            throw new FailException("Tài khoản của bạn đã bị khóa", 2004);
        }
        ProviderResLoginDto providerResLoginDto = ProviderMapper.mapToProviderResLoginDTo(provider);
        return providerResLoginDto;
    }

    @Override
    public void changeStatusProvider(Integer id, StatusEnum statusEnum) {
        Provider provider = providerRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1055));
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1056));
        provider.setStatus(status);
        try {
            providerRepository.save(provider);
        } catch (Exception e) {
            throw new FailException("Can't update status provider", 2003);
        }
    }

    @Override
    public ResponseProviderDto getAllProviderByStatus(StatusEnum statusEnum, Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1111));
        List<Provider> providers = providerRepository.findAllByStatusId(status.getId(), "%" + keyword + "%", pageable);
        int count = providerRepository.countAllByStatusId(status.getId(), "%" + keyword + "%");
        Integer total = (Integer) totalTourPages(count);
        ResponseProviderDto responseProviderDto = new ResponseProviderDto();
        List<ProviderResLoginDto> providerResLoginDtos = new ArrayList<>();
        for (Provider provider : providers) {
            ProviderResLoginDto providerResLoginDto = ProviderMapper.mapToProviderResLoginDTo(provider);
            providerResLoginDtos.add(providerResLoginDto);
        }
        responseProviderDto.setProviders(providerResLoginDtos);
        responseProviderDto.setTotal(total);
        responseProviderDto.setPage(page);
        return responseProviderDto;
    }

    @Override
    public ResponseEntity getTotalDashboard( Integer providerId) {
        Status status = statusRepository.findByName(StatusEnum.ACCEPT).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1050));
        boolean test = false;
        Integer totalTour = tourRepo.countAllByProvider(providerId, status.getId(), test);
        Integer totalSale = paymentRepo.countAllByStatusAndProvider("COMPLETE", providerId);
        Map<String, Object> objectMap = new HashMap<>();
        Integer percent = adminRepository.percent();
        objectMap.put("totalTour", totalTour);
        if (totalSale != null) {
            objectMap.put("totalSale", Double.valueOf(totalSale * Double.valueOf(100 - percent)/100.0));
        }else {
            objectMap.put("totalSale", 0.0);
        }
        return ResponseEntity.ok(ResponseDto.response(objectMap));
    }

    @Override
    public ResponseEntity getChatPaymentDashboard(Integer providerId, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        Integer percent = adminRepository.percent();
        List<Payment> payments = paymentRepo.findAllByStatusAndDAndCreateAtAndProvider( start, end, providerId);
        List<Double> acc = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            acc.add(0.0);
        }
        for (Payment payment : payments) {
            switch (payment.getCreateAt().getMonth().getValue()) {
                case 1: {
                    acc.set(0, acc.get(0) + Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 2: {
                    acc.set(1, acc.get(1) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 3: {
                    acc.set(2, acc.get(2) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 4: {
                    acc.set(3, acc.get(3) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 5: {
                    acc.set(4, acc.get(4) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 6: {
                    acc.set(5, acc.get(5) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 7: {
                    acc.set(6, acc.get(6) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 8: {
                    acc.set(7, acc.get(7) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 9: {
                    acc.set(8, acc.get(8) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 10: {
                    acc.set(9, acc.get(9) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 11: {
                    acc.set(10, acc.get(10) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
                    break;
                }
                case 12: {
                    acc.set(11, acc.get(11) +  Double.valueOf(payment.getTotal()*Double.valueOf(100 - percent)/100.0));
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
    public ProviderResLoginDto getDetail(Integer id) {
        Provider provider = providerRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1051));
        ProviderResLoginDto resLoginDto = ProviderMapper.mapToProviderResLoginDTo(provider);
        return resLoginDto;
    }

    @Override
    @Transactional
    public void editProvider(ProviderReq providerReq) {
        Provider provider = providerRepository.findById(providerReq.getId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1056));
        Account account = accountRepository.findById(provider.getAccount().getId()).
                orElseThrow(() -> new NotFoundException("Not Found account.", 1057));
        Bank bank = bankRepo.findBankById(providerReq.getBankId()).
                orElseThrow(() -> new NotFoundException("Not found bank.", 1058));
        try {
            account.setUsername(providerReq.getUsername());
            account.setEmail(providerReq.getEmail());
            account.setPassword(providerReq.getPassword());
            accountRepository.save(account);
            provider.setNameCompany(providerReq.getNameCompany());
            provider.setOwner(providerReq.getOwner());
            provider.setPhoneNumber(provider.getPhoneNumber());
            provider.setAddress(providerReq.getAddress());
            provider.setBankNumber(provider.getBankNumber());
            provider.setBank(bank);
            providerRepository.save(provider);
        } catch (Exception e) {
            throw new FailException("Can't update provider", 2003);
        }
    }
}
