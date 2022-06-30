package com.example.dnTravelBE.controller;

import com.example.dnTravelBE.dto.ResponseDto;
import com.example.dnTravelBE.entity.Category;
import com.example.dnTravelBE.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
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

    @GetMapping("/admin")
    public ResponseEntity<Object> getByAdmin(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                             @RequestParam(defaultValue = "") String keyword){
        return categoryService.getByAdmin(page, keyword);
    }

    @PostMapping("/admin/{name}")
    public ResponseEntity<Object> careteCate(@PathVariable String name) {
        categoryService.createCate(name);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }

    @PutMapping ("/admin/{id}/{name}")
    public ResponseEntity<Object> editCate(@PathVariable("name") String name,
                                           @PathVariable("id") Integer id) {
        categoryService.editCate(id, name);
        return ResponseEntity.ok(ResponseDto.responseWithoutData());
    }
}
