package com.example.dnTravelBE.service;

import com.example.dnTravelBE.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<Object> getCategories();
    ResponseEntity getByAdmin(Integer page, String keyword);
    void createCate(String name);
    void editCate(Integer id, String name);

}
