package org.example.projectmodule6renthousebe.service;

import org.example.projectmodule6renthousebe.model.Image;

import java.util.List;

public interface ImageService extends IGenerateService<Image>{
    Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag);
    Iterable<Image> addImagesToHouse(Long houseId, List<String> imageUrls);
}
