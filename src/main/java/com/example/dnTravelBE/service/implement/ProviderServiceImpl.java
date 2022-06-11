package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.*;
import com.example.dnTravelBE.entity.*;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.mapper.ProviderMapper;
import com.example.dnTravelBE.mapper.TourMapper;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.BankRepo;
import com.example.dnTravelBE.repository.ProviderRepository;
import com.example.dnTravelBE.repository.StatusRepository;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final StatusRepository statusRepository;
    private final ProviderRepository providerRepository;
    private final AccountRepository accountRepository;
    private final BankRepo bankRepo;

    private static int sizePage = 5;

    public int totalTourPages(StatusEnum statusEnum) {
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1112));
        int count = providerRepository.countAllByStatus(status);
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
            throw new FailException("Your account has not been verified", 2004);
        }
        if (provider.getStatus().getName() == StatusEnum.REFUSE ){
            throw new FailException("YYour account has been locked", 2004);
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
            throw new FailException("Cann't update status provider", 2003);
        }
    }

    @Override
    public ResponseProviderDto getAllProviderByStatus(StatusEnum statusEnum, Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        Status status = statusRepository.findByName(statusEnum).
                orElseThrow(() -> new NotFoundException("Not Found status.", 1111));
        List<Provider> providers = providerRepository.findAllByStatusId(status.getId(), "%" + keyword + "%", pageable);
        Integer total = (Integer) totalTourPages(statusEnum);
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
}
