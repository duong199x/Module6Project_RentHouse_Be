package org.example.projectmodule6renthousebe.service.impl;

import org.example.projectmodule6renthousebe.model.House;
import org.example.projectmodule6renthousebe.model.Image;
import org.example.projectmodule6renthousebe.repository.ImageRepository;
import org.example.projectmodule6renthousebe.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Iterable<Image> findAllByDeleteFlag(boolean deleteFlag) {
        return null;
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
        image.ifPresent(value -> value.setDeleteFlag(true));
    }
}
