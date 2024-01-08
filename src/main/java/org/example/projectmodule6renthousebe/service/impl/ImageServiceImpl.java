package org.example.projectmodule6renthousebe.service.impl;

import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.model.Image;
import org.example.projectmodule6renthousebe.repository.HouseRepository;
import org.example.projectmodule6renthousebe.repository.ImageRepository;
import org.example.projectmodule6renthousebe.service.ImageService;
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
    public Iterable<Image> findAllByHouseId(Long houseId) {
        return imageRepository.findAllByHouseId(houseId);
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

        // Lấy danh sách ảnh đã được thêm vào nhà
        return imageRepository.findAllByHouseId(houseId);
    }
}
