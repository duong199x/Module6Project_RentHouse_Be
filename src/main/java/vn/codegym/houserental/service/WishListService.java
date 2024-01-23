package vn.codegym.houserental.service;

import org.springframework.stereotype.Service;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.WishList;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.repository.BookingRepository;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.repository.UserRepository;
import vn.codegym.houserental.repository.WishListRepository;
import vn.codegym.houserental.requests.WishListRequest;
import vn.codegym.houserental.response.WishlistResponse;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public WishListService(WishListRepository wishListRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }
    public WishList save(WishListRequest wishListRequest) throws CommonException{
        WishList wishList = new WishList();
        User user = userRepository.findById(wishListRequest.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
        House house = houseRepository.findById(wishListRequest.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));
        wishList.setUser(user);
        wishList.setHouse(house);
        wishList.setCreatedAt(LocalDateTime.now());
        return wishListRepository.save(wishList);

    }
    public List<WishlistResponse> findAllByUserId(Long userId){
        return wishListRepository.findAllWishlists(userId);
    }
    public void deleteWishList(Long wishListId) {
        Optional<WishList> wishList = wishListRepository.findById(wishListId);
        if (wishList.isPresent()){
            wishList.get().setDeleteFlag(true);
            wishListRepository.save(wishList.get());
        }

    }
}
