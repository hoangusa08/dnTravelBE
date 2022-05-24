package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.RegisterProviderDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    @Override
    public void createProvider(RegisterProviderDto registerProviderDto, Account account) {

    }
}
