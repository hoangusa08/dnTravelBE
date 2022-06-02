package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> getCategories() {
        List<Object> categories = categoryService.getCategories();
        Map<String, Object> response = ResponseDto.response(categories);
        return ResponseEntity.ok(response);
    }
}
