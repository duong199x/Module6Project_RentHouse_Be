package vn.codegym.houserental.service;

import vn.codegym.houserental.model.Image;

import java.util.List;

public interface ImageService extends IGenerateService<Image>{
    Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag);
    Iterable<Image> addImagesToHouse(Long houseId, List<String> imageUrls);
}
