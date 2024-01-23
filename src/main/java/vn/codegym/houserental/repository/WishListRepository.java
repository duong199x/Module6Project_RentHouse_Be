package vn.codegym.houserental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.codegym.houserental.model.WishList;
import vn.codegym.houserental.response.HistoryResponse;
import vn.codegym.houserental.response.WishlistResponse;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Long> {
    @Query("SELECT new vn.codegym.houserental.response.WishlistResponse(w.id,h.id,u.id,h.name,h.description,h.price,h.location,c.name,h.user.fullName,w.createdAt) FROM WishList w" +
            " LEFT JOIN House h ON w.house.id = h.id" +
            " LEFT JOIN Category c ON h.category.id = c.id" +
            " LEFT JOIN User u ON w.user.id = u.id" +
            " WHERE 1 = 1" +
            "AND w.deleteFlag = false")
//    List<HistoryResponse> getHistories(@Param("userId") Long userId);
    List<WishlistResponse> findAllWishlists(@Param("userId") Long userId);
}
