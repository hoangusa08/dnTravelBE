package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.repository.CategoryRepo;
import com.example.dnTravelBE.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    @Override
    public List<Object> getCategories() {

        List<Category> categories = categoryRepo.findAll();
        List<Object> res = new ArrayList<>();
        for (Category category: categories){
            Map<String, Object> response = new HashMap<>();
            response.put("id", category.getId());
            response.put("name", category.getName());
            res.add(response);
        }
        return res;
    }
}
