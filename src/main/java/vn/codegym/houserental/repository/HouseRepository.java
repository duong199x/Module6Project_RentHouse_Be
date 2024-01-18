package vn.codegym.houserental.repository;

import com.google.firebase.database.annotations.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.codegym.houserental.model.Convenient;
import vn.codegym.houserental.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
    Iterable<House> findAllByDeleteFlag(boolean deleteFlag);
    Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId);
//    Page<House> findAllByDeleteFlagAndCategoryId(Pageable pageable, Long categoriesId);
    Iterable<House> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
    Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name,boolean deleteFlag);
    @Query("SELECT h FROM House h JOIN h.convenients c " +
            "WHERE (:name IS NULL OR :name = '' OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:location IS NULL OR :location = '' OR LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:categoryId IS NULL OR :categoryId = 0 OR h.category.id = :categoryId) AND " +
            "(:minPrice IS NULL OR :minPrice = 0.0 OR h.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR :maxPrice = 0.0 OR h.price <= :maxPrice) AND " +
            "(:convenientIds IS NULL OR (0 IN :convenientIds OR c.id IN :convenientIds)) AND " +
            "h.deleteFlag = :deleteFlag")
    List<House> findHousesByCriteria(
            @Param("name") String name,
            @Param("location") String location,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            @Param("convenientIds") List<Long> convenientIds,
            @Param("deleteFlag") boolean deleteFlag
    );
}
