package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Customer;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.repository.CustomerRepository;
import com.example.dnTravelBE.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public void createCustomer(RegisterCustomerDto registerCustomerDto, Account newAccountCustomer) {
        try {
            Customer customer = new Customer();
            customer.setFullName(registerCustomerDto.getFullname());
            customer.setAccount(newAccountCustomer);
            customer.setAddress(registerCustomerDto.getAddress());
            customer.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            customerRepository.save(customer);
        }catch (Exception e) {
            throw new FailException("Cann't create customer", 2002);
        }
    }
}
