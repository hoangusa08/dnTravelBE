package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.entity.Bank;
import com.example.dnTravelBE.repository.BankRepo;
import com.example.dnTravelBE.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BankServiceImpl implements BankService {

    private final BankRepo bankRepo;
    @Override
    public List<Object> getAll() {
        List<Bank> banks = bankRepo.findAll();
        List<Object> objects = new ArrayList<>();
        for (Bank bank : banks){
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("id", bank.getId());
            objectMap.put("name", bank.getName());
            objects.add(objectMap);
        }
        return objects;
    }
}
