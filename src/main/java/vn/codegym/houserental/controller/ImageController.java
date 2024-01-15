package vn.codegym.houserental.controller;

import vn.codegym.houserental.model.Image;
import vn.codegym.houserental.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin("*")
public class ImageController {
    @Autowired
    private ImageServiceImpl imageService;

    @GetMapping("")
    public ResponseEntity<List<Image>> showAllImage() {
        List<Image> productImages = (List<Image>) imageService.findAllByDeleteFlag(false);
        if (productImages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productImages, HttpStatus.OK);
    }

    @PostMapping("/create/{houseId}")
    public ResponseEntity<Iterable<Image>> save(@PathVariable Long houseId, @RequestBody List<String> productImages) {
        return new ResponseEntity<>(imageService.addImagesToHouse(houseId, productImages), HttpStatus.OK);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<List<Image>> getAllByHouseId(@PathVariable Long houseId) {
        List<Image> images = (List<Image>) imageService.findAllByHouseIdAndDeleteFlag(houseId,false);
        if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Image> delete(@PathVariable Long id) {
        imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
