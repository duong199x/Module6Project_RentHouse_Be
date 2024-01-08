package org.example.projectmodule6renthousebe.service.impl;

import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.repository.HouseRepository;
import org.example.projectmodule6renthousebe.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Override
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public Iterable<House> findAllByDeleteFlag(boolean deleteFlag) {
        return houseRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<House> findOneById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void delete(Long id) {
        Optional<House> house = houseRepository.findById(id);
        if (house.isPresent()) {
            house.get().setDeleteFlag(true);
            houseRepository.save(house.get());
        }
    }

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Override
    public Page<House> getAllHousesSortedByPriceUp(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("price").ascending());
        return houseRepository.findAll(pageRequest);
    }

    @Override
    public Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId) {
        return houseRepository.findAllByCategoryId(pageable, categoriesId);
    }
}
