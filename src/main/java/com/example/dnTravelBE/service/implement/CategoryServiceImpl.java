package com.example.dnTravelBE.service.implement;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.exception.FailException;
import com.example.dnTravelBE.repository.CategoryRepo;
import com.example.dnTravelBE.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    private static int sizePage = 10;

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

    @Override
    public ResponseEntity getByAdmin( Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, sizePage);
        List<Category> categories = categoryRepo.findAllByAdmin("%" + keyword + "%", pageable);
        List<Object> cas = new ArrayList<>();
        for (Category category: categories){
            Map<String, Object> response = new HashMap<>();
            response.put("id", category.getId());
            response.put("name", category.getName());
            cas.add(response);
        }
        int total = categoryRepo.countAll("%" + keyword + "%");
        Map<String, Object> res = new HashMap<>();
        res.put("page", page);
        res.put("total", totalPages(total));
        res.put("categories", cas);
        return ResponseEntity.ok(ResponseDto.response(res));
    }

    @Override
    public void createCate(String name) {
        Optional<Category> category = categoryRepo.findByName(name);
        if(!category.isEmpty()){
            throw new FailException("Thể loại đã tồn tại", 1112);
        }
        Category category1 = new Category();
        category1.setName(name);
        try {
            categoryRepo.save(category1);
        }catch (Exception e) {
            throw new FailException("Can't create an category", 1113);
        }
    }

    @Override
    public void editCate(Integer id, String name) {
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isEmpty()) {
            throw new FailException("Thể loại không tồn tại", 1112);
        }
        category.get().setName(name);
        try {
            categoryRepo.save(category.get());
        }catch (Exception e) {
            throw new FailException("Can't create an category", 1113);
        }
    }
}
