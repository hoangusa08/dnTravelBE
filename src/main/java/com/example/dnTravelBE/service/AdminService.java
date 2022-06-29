package com.example.dnTravelBE.service;

import com.example.dnTravelBE.dto.AdminLoginResDto;
import org.springframework.http.ResponseEntity;

public interface AdminService {
 AdminLoginResDto getInfoWhenLogin( String email);

 ResponseEntity getTotalDashboard();

 ResponseEntity getChartUserDashboard( int year );

 ResponseEntity getChatPaymentDashboard( int year);

 void deleteComment(Integer id);
}
