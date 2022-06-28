package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.constant.StatusEnum;
import com.example.dnTravelBE.dto.CustomerResponseLoginDto;
import com.example.dnTravelBE.dto.RegisterCustomerDto;
import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Customer;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.CustomerRepository;
import com.example.dnTravelBE.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private static int sizePage = 5;

    public int totalPages(int count) {
        if (count <= sizePage) {
            return 1;
        } else if (count % sizePage != 0) {
            return (int) Math.floor(count / sizePage) + 1;
        } else {
            return (int) Math.floor(count / sizePage);
        }
    }

    @Override
    public void createCustomer(RegisterCustomerDto registerCustomerDto, Account newAccountCustomer) {
        try {
            Customer customer = new Customer();
            customer.setFullName(registerCustomerDto.getFullname());
            customer.setAccount(newAccountCustomer);
            customer.setAddress(registerCustomerDto.getAddress());
            customer.setPhoneNumber(registerCustomerDto.getPhoneNumber());
            customer.setDelete(false);
            customerRepository.save(customer);

            System.out.println("adb");
        } catch (Exception e) {
            throw new FailException("Cann't create customer", 2002);
        }
    }

    @Override
    public CustomerResponseLoginDto getCustomerWhenLogin(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Not Found Account.", 1005));
        Customer customer = customerRepository.findByAccount_Id(account.getId())
                .orElseThrow(() -> new NotFoundException("Not Found Cutomer.", 1006));

        if (customer.isDelete()){
            throw new FailException("Tài khoản của bạn đã bị khóa", 2004);
        }
        CustomerResponseLoginDto customerResponseLoginDto = new CustomerResponseLoginDto(
                customer.getAddress(),
                customer.getFullName(),
                customer.getId(),
                customer.getPhoneNumber(),
                account.getRole().getName().toString(),
                account.getUsername(),
                account.getEmail(),
                account.getAvatar(),
                customer.isDelete()
        );
        return customerResponseLoginDto;
    }

    @Override
    public CustomerResponseLoginDto getCustomerDetail(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Cutomer.", 1021));
        Account account = accountRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new NotFoundException("Not Found Account.", 1205));
        CustomerResponseLoginDto customerResponseLoginDto = new CustomerResponseLoginDto(
                customer.getAddress(),
                customer.getFullName(),
                customer.getId(),
                customer.getPhoneNumber(),
                account.getRole().getName().toString(),
                account.getUsername(),
                account.getEmail(),
                account.getAvatar(),
                customer.isDelete()
        );
        return customerResponseLoginDto;
    }

    @Override
    public void updateCustomerDetail(CustomerResponseLoginDto customerResponseLoginDto) {

        Customer customer = customerRepository.findById(customerResponseLoginDto.getId())
                .orElseThrow(() -> new NotFoundException("Not Found Customer.", 1023));

        Account account = accountRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new NotFoundException("Not Found Account.", 1024));

        account.setUsername(customerResponseLoginDto.getUsername());
        account.setEmail(customerResponseLoginDto.getEmail());
        customer.setAddress(customerResponseLoginDto.getAddress());
        customer.setFullName(customerResponseLoginDto.getFullName());
        customer.setPhoneNumber(customerResponseLoginDto.getPhoneNumber());
        try {
            accountRepository.save(account);
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new FailException("Cann't update customer", 2002);
        }

    }

    @Override
    public ResponseEntity getAllCustomer(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        List<Customer> customers = customerRepository.findAllByFullName("%" + keyword + "%", pageable);
        List<CustomerResponseLoginDto> customerResponseLoginDtos = new ArrayList<>();
        Map<String, Object> responses =new HashMap<>();
        for (Customer customer : customers) {
            CustomerResponseLoginDto customerResponseLoginDto = new CustomerResponseLoginDto();
            customerResponseLoginDto.setAddress(customer.getAddress());
            customerResponseLoginDto.setFullName(customer.getFullName());
            customerResponseLoginDto.setId(customer.getId());
            customerResponseLoginDto.setPhoneNumber(customer.getPhoneNumber());
            customerResponseLoginDto.setRole(customer.getAccount().getRole().getName().toString());
            customerResponseLoginDto.setUsername(customer.getAccount().getUsername());
            customerResponseLoginDto.setEmail(customer.getAccount().getEmail());
            customerResponseLoginDto.setAvatar(customer.getAccount().getAvatar());
            customerResponseLoginDto.setDelete(customer.isDelete());
            customerResponseLoginDtos.add(customerResponseLoginDto);
        }

        int total = customerRepository.countAllByFullName("%" + keyword + "%");
        int totalPage = totalPages(total);
        responses.put("total", totalPage );
        responses.put("customers", customerResponseLoginDtos );
        responses.put("page", page);
        return ResponseEntity.ok(ResponseDto.response(responses));
    }

}
