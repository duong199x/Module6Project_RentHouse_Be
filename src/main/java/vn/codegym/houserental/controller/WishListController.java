package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.houserental.dto.CommentDTO;
import vn.codegym.houserental.dto.WishListDTO;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.WishList;
import vn.codegym.houserental.requests.WishListRequest;
import vn.codegym.houserental.response.ApiResponse;
import vn.codegym.houserental.response.bookingresponse.CancelBookingResponse;
import vn.codegym.houserental.service.WishListService;
import vn.codegym.houserental.utils.ModelMapperUtil;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/wishlist")

public class WishListController {
    @Autowired
    private ModelMapperUtil mapperUtil;
    @Autowired
    private WishListService wishListService;

    @GetMapping("{userId}")
    public ResponseEntity<List<WishListDTO>> showAllByUser(@PathVariable Long userId) {
        List<WishList> wishLists = wishListService.findAllByUserId(userId);
        if (wishLists.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(wishLists, WishListDTO.class), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveWishList(@RequestBody WishListRequest wishListRequest) {
        try {
            WishList wishList = wishListService.save(wishListRequest);
            return new ResponseEntity<>(wishList,HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long wishListId){
        try {
            wishListService.deleteWishList(wishListId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CancelBookingResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }
}
