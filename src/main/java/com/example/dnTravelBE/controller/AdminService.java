package com.example.dnTravelBE.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminService {

    @GetMapping("providerAccept")
    private ResponseEntity<Object> getProvidersAccept() {
        return null;
    }

    @GetMapping("providerRefuse")
    private ResponseEntity<Object> getProvidersRefuse () {
        return null;
    }

    @GetMapping("providerWait")
    private ResponseEntity<Object> getProvidersWait () {
    return null;
    }

    @GetMapping("customerAccept")
    private ResponseEntity<Object> getCustomersAccept() {
        return null;
    }

    @GetMapping("customerRefuse")
    private ResponseEntity<Object> getCustomerRefuse () {
        return null;
    }

    @GetMapping("customerWait")
    private ResponseEntity<Object> getCustomerWait () {
        return null;
    }

    @GetMapping("tourWait")
    private ResponseEntity<Object> getToursWait () {
        return null;
    }

    @GetMapping("tourRefuse")
    private ResponseEntity<Object> getToursRefuse () {
        return null;
    }

    @GetMapping("tourAccept")
    private ResponseEntity<Object> getToursAccept () {
        return null;
    }
}
