package vn.codegym.houserental.repository;

import vn.codegym.houserental.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Iterable<Image> findAllByDeleteFlag(boolean deleteFlag);
    Iterable<Image> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag);

}
