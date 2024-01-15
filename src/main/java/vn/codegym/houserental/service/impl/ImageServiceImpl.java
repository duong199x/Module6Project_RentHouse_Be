package vn.codegym.houserental.service.impl;

import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.Image;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.repository.ImageRepository;
import vn.codegym.houserental.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Iterable<Image> findAllByDeleteFlag(boolean deleteFlag) {
        return imageRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Optional<Image> findOneById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            image.get().setDeleteFlag(true);
            imageRepository.save(image.get());
        }
    }

    @Override
    public Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag) {
        return imageRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }

    @Override
    @Transactional
    public Iterable<Image> addImagesToHouse(Long houseId, List<String> imageUrls) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new RuntimeException("House not found"));

        for (String imageUrl : imageUrls) {
            Image image = new Image();
            image.setImage(imageUrl);
            image.setHouse(house);
            imageRepository.save(image);
        }
        return imageRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }
}
