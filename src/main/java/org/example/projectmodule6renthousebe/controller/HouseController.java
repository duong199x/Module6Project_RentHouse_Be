package org.example.projectmodule6renthousebe.controller;

import org.example.projectmodule6renthousebe.dto.HouseDTO;
import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.requests.CreateHouseRequest;
import org.example.projectmodule6renthousebe.response.CreateHouseResponse;
import org.example.projectmodule6renthousebe.service.impl.HouseServiceImpl;
import org.example.projectmodule6renthousebe.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
@CrossOrigin("*")
public class HouseController {
    @Autowired
    private HouseServiceImpl houseService;
    @Autowired
    private ModelMapperUtil mapperUtil;

    @GetMapping("")
    public ResponseEntity<List<HouseDTO>> showAll() {
        List<House> house = (List<House>) houseService.findAllByDeleteFlag(false);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<CreateHouseResponse> save(@RequestBody CreateHouseRequest request) {
        try {
            House saveHouse = houseService.save(request);
            List<String> imageList = request.getImages();
            houseService.saveImageListAsync(saveHouse, imageList);
            return new ResponseEntity<>(new CreateHouseResponse(true, "MS-HO-01"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateHouseResponse(false, "ER-HO-01"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<House> delete(@PathVariable Long id) {
        houseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<House> update(@PathVariable Long id, @RequestBody House house) {
        house.setId(id);
        houseService.save(house);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> findById(@PathVariable Long id) {
        Optional<House> house = houseService.findOneById(id);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.map(house, HouseDTO.class), HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<Page<House>> getAllHouse(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam String sortOrder
    ) {
        Pageable pageable;
        if (sortOrder.isEmpty()) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "price");
            pageable = PageRequest.of(page, size, sort);
        }

        Page<House> houses = houseService.findAll(pageable);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/searchByCate")
    public ResponseEntity<Page<House>> searchByCategory(@PageableDefault(value = 12) Pageable pageable, @RequestBody Long categoriesId) {
        if (categoriesId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Page<House> houses = houseService.findAllByCategoryId(pageable, categoriesId);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/{houseId}/addConvenient")
    public ResponseEntity<String> addConvenientsToHouse(
            @PathVariable Long houseId,
            @RequestBody List<Long> convenientIds
    ) {
        try {
            houseService.addConvenientsToHouse(houseId, convenientIds);
            return ResponseEntity.ok("Convenients added to house successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding convenients to house: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HouseDTO>> getAllByUserId(@PathVariable Long userId) {
        List<House> house = (List<House>) houseService.findAllByUserIdAndDeleteFlag(userId, false);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HouseDTO>> searchByName(@RequestParam String name) {
        List<House> house = (List<House>) houseService.findByNameContainsIgnoreCaseAndDeleteFlag(name, false);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }


}
