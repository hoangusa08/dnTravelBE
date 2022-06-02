package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.entity.Province;
import com.example.dnTravelBE.repository.ProvinceRepo;
import com.example.dnTravelBE.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepo provinceRepo;
    @Override
    public List<Province> getProvinces() {
        List<Province> provinces = provinceRepo.findAll();
        return provinces;
    }
}
