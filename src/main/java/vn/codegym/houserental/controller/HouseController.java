package vn.codegym.houserental.controller;

import vn.codegym.houserental.dto.HouseDTO;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.HouseStatus;
import vn.codegym.houserental.requests.CreateHouseRequest;
import vn.codegym.houserental.requests.SearchRequest;
import vn.codegym.houserental.response.CreateHouseResponse;
import vn.codegym.houserental.response.DeleteHouseResponse;
import vn.codegym.houserental.service.impl.HouseServiceImpl;
import vn.codegym.houserental.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            House saveHouse = houseService.saveOrUpdateHouse(request);
            List<String> imageList = request.getImages();
            houseService.saveImageListAsync(saveHouse, imageList);
            return new ResponseEntity<>(new CreateHouseResponse("MS-HO-01"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateHouseResponse("ER-HO-01"), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<CreateHouseResponse> update(@PathVariable Long id, @RequestBody CreateHouseRequest request) {
        try {
            request.setId(id);
            houseService.saveOrUpdateHouse(request);       
            return new ResponseEntity<>(new CreateHouseResponse("MS-HO-01"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CreateHouseResponse("ER-HO-01"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteHouseResponse> delete(@PathVariable Long id) {
        try {
            houseService.delete(id);
            return new ResponseEntity<>(new DeleteHouseResponse("MS-H3-01"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DeleteHouseResponse("ER-H3-01"), HttpStatus.BAD_REQUEST);
        }
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
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HouseDTO>> getAllByUserId(@PathVariable Long userId) {
        List<House> house = (List<House>) houseService.findAllByUserIdAndDeleteFlag(userId, false);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<HouseDTO>> searchByCondition(@RequestBody SearchRequest request) {
        List<House> house =  houseService.findByCondition(request);
        if (house.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(house, HouseDTO.class), HttpStatus.OK);
    }





}
