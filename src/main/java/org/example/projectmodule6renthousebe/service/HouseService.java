package org.example.projectmodule6renthousebe.service;

import org.example.projectmodule6renthousebe.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HouseService extends IGenerateService<House> {
    Page<House> findAll(Pageable pageable);
    Page<House> getAllHousesSortedByPriceUp(int page, int size);
    Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId);
}
