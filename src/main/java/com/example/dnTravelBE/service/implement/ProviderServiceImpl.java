package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Provider;
import com.example.dnTravelBE.entity.Status;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.ProviderRepository;
import com.example.dnTravelBE.repository.StatusRepository;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final StatusRepository statusRepository;
    private final ProviderRepository providerRepository;
    @Override
    public void createProvider(RegisterProviderDto registerProviderDto, Account account) {

        Status status = statusRepository.findByName(StatusEnum.WAIT)
                .orElseThrow(() -> new NotFoundException("Status input invalid" , 1032));
        try {
            Provider provider = new Provider();
            provider.setAccount(account);
            provider.setNameCompany(registerProviderDto.getNameCompany());
            provider.setAddress(registerProviderDto.getAddress());
            provider.setStatus(status);
            provider.setBankNumber(registerProviderDto.getBankNumber());
            provider.setPhoneNumber(registerProviderDto.getPhoneNumber());
            providerRepository.save(provider);
        }catch (Exception e) {
            throw new FailException("Cann't create provider", 2002);
        }
    }
}
