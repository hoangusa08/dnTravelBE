package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.AccountRole;
import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.ProviderResLoginDto;
import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Provider;
import com.example.dnTravelBE.entity.Status;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.ProviderRepository;
import com.example.dnTravelBE.repository.StatusRepository;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final StatusRepository statusRepository;
    private final ProviderRepository providerRepository;
    private final AccountRepository accountRepository;
    @Override
    public void createProvider(RegisterProviderDto registerProviderDto, Account account) {

        Status status = statusRepository.findByName(StatusEnum.WAITING)
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

    @Override
    public ProviderResLoginDto getProviderWhenLogin(String email) {
        Account account = accountRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("Not Found Account.", 1023));
        System.out.println(account.getId()+"");
        Provider provider = providerRepository.findByAccount_Id(account.getId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        System.out.println("abc");
        ProviderResLoginDto providerResLoginDto = new ProviderResLoginDto(
                provider.getNameCompany(),
                provider.getOwner(),
                provider.getId(),
                provider.getPhoneNumber(),
                AccountRole.ROLE_PROVIDER.toString(),
                provider.getAddress(),
                provider.getBankNumber()
        );
        System.out.println("abc12");
        return providerResLoginDto;
    }
}
