package vn.codegym.houserental.repository;

import vn.codegym.houserental.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.houserental.model.HouseStatus;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
    Iterable<House> findAllByDeleteFlag(boolean deleteFlag);
    Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId);
//    Page<House> findAllByDeleteFlagAndCategoryId(Pageable pageable, Long categoriesId);
    Iterable<House> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
    Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name,boolean deleteFlag);

}
