package com.example.dnTravelBE.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentControler {
}
