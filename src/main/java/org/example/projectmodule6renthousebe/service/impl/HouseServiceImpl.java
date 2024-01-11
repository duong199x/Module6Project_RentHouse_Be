package org.example.projectmodule6renthousebe.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.projectmodule6renthousebe.model.Convenient;
import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.model.Image;
import org.example.projectmodule6renthousebe.repository.ConvenientRepository;
import org.example.projectmodule6renthousebe.repository.HouseRepository;
import org.example.projectmodule6renthousebe.requests.CreateHouseRequest;
import org.example.projectmodule6renthousebe.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private ConvenientRepository convenientRepository;
    @Autowired
    private ImageServiceImpl imageService;

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
        return null;
    }

    public House save(CreateHouseRequest request) {
        House house = new House();
        house.setName(request.getName());
        house.setCategory(request.getCategory());
        house.setBedRoom(request.getBedRoom());
        house.setDescription(request.getDescription());
        house.setKitchen(request.getKitchen());
        house.setPrice(request.getPrice());
        house.setUser(request.getUser());
        house.setLocation(request.getLocation());
        house.setBathRoom(request.getBathRoom());
        house.setLivingRoom(request.getLivingRoom());
        Set<Convenient> newConvenients = new HashSet<>();
        if (request.getConvenientIds() != null && !request.getConvenientIds().isEmpty()) {
            for (Long convenientId : request.getConvenientIds()) {
                Convenient convenient = convenientRepository.findById(convenientId)
                        .orElseThrow(() -> new EntityNotFoundException("Convenient with id " + convenientId + " not found."));
                newConvenients.add(convenient);
            }
        }
        house.setConvenients(newConvenients);
        return houseRepository.save(house);
    }
    @Async
    public CompletableFuture<Void> saveImageListAsync(House house, List<String> imageList) {
        imageList.forEach(image -> {
            Image imageEntity = new Image();
            imageEntity.setHouse(house);
            imageEntity.setImage(image);
            imageService.save(imageEntity);
        });

        return CompletableFuture.completedFuture(null);
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

    @Override
    public Iterable<House> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag) {
        return houseRepository.findAllByUserIdAndDeleteFlag(userId,deleteFlag);
    }

    @Override
    public Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name, boolean deleteFlag) {
        return houseRepository.findByNameContainsIgnoreCaseAndDeleteFlag(name,deleteFlag);
    }

//    @Transactional
//    public void addConvenientsToHouse(Long houseId, List<Long> convenientIds) {
//        Optional<House> houseOptional = houseRepository.findById(houseId);
//        if (houseOptional.isPresent()) {
//            House house = houseOptional.get();
//            Set<Convenient> convenients = house.getConvenients();
//            convenients.addAll(convenient.findAllById(convenientIds));
//
//            houseRepository.save(house);
//        } else {
//            throw new RuntimeException("House not found");
//        }
//    }
}
