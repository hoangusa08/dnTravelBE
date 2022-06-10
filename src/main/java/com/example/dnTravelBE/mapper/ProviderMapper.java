package com.example.dnTravelBE.mapper;

import com.example.dnTravelBE.dto.ProviderResLoginDto;
import com.example.dnTravelBE.entity.Provider;

public class ProviderMapper {

    public static ProviderResLoginDto mapToProviderResLoginDTo(Provider provider) {
        ProviderResLoginDto providerResLoginDto = new ProviderResLoginDto();
        providerResLoginDto.setId(provider.getId());
        providerResLoginDto.setAddress(provider.getAddress());
        providerResLoginDto.setBankNumber(provider.getBankNumber());
        providerResLoginDto.setPhoneNumber(provider.getPhoneNumber());
        providerResLoginDto.setOwner(provider.getOwner());
        providerResLoginDto.setNameConpany(provider.getNameCompany());
        providerResLoginDto.setRole(provider.getAccount().getRole().getName().toString());
        return providerResLoginDto;
    }
}
