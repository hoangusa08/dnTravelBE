package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.repository.CategoryRepo;
import com.example.dnTravelBE.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    @Override
    public List<Category> getCategories() {

        List<Category> categories = categoryRepo.findAll();
        System.out.println("abc");
        return categories;
    }
}
