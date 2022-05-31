package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.AdminLoginResDto;
import com.example.dnTravelBE.entity.Account;
import com.example.dnTravelBE.entity.Admin;
import com.example.dnTravelBE.entity.Provider;
import com.example.dnTravelBE.exception.NotFoundException;
import com.example.dnTravelBE.repository.AccountRepository;
import com.example.dnTravelBE.repository.AdminRepository;
import com.example.dnTravelBE.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    private final AccountRepository accountRepository;
    private final AdminRepository  adminRepository;
    @Override
    public AdminLoginResDto getInfoWhenLogin(String email) {
        Account account = accountRepository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("Not Found Account.", 1031));
        Admin admin = adminRepository.findByAccount_Id(account.getId()).
                orElseThrow(() -> new NotFoundException("Not Found provider.", 1005));
        AdminLoginResDto adminLoginResDto = new AdminLoginResDto();
        adminLoginResDto.setId(admin.getId());
        return adminLoginResDto;
    }
}
