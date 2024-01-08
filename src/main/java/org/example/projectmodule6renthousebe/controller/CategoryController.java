package org.example.projectmodule6renthousebe.controller;

import org.example.projectmodule6renthousebe.dto.HouseDTO;
import org.example.projectmodule6renthousebe.model.Category;
import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.repository.CategoryRepository;
import org.example.projectmodule6renthousebe.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;
    @GetMapping("")
    public ResponseEntity<List<Category>> showAll() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
