package vn.codegym.houserental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.houserental.model.WishList;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
}
