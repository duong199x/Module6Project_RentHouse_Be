package vn.codegym.houserental.service;

import vn.codegym.houserental.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.codegym.houserental.requests.SearchRequest;
import java.util.List;

public interface HouseService extends IGenerateService<House> {
    Page<House> findAll(Pageable pageable);
    Page<House> getAllHousesSortedByPriceUp(int page, int size);
    Page<House> findAllByCategoryId(Pageable pageable, Long categoriesId);
    Iterable<House> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
    Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name,boolean deleteFlag);
    List<House> findByCondition(SearchRequest request);
}
