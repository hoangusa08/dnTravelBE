package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.AdminLoginResDto;

public interface AdminService {
 AdminLoginResDto getInfoWhenLogin( String email);
}
