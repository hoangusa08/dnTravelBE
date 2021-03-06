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
        providerResLoginDto.setNameCompany(provider.getNameCompany());
        providerResLoginDto.setEmail(provider.getAccount().getEmail());
        providerResLoginDto.setRole(provider.getAccount().getRole().getName().toString());
        providerResLoginDto.setUsername(provider.getAccount().getUsername());
        providerResLoginDto.setBankId(provider.getBank().getId());
        return providerResLoginDto;
    }
}
