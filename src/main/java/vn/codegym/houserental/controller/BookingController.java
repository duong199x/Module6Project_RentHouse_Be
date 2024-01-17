package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import vn.codegym.houserental.dto.BookingDTO;
import vn.codegym.houserental.dto.HouseDTO;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.requests.BookingRequest;
import vn.codegym.houserental.response.ApiResponse;
import vn.codegym.houserental.response.DeleteHouseResponse;
import vn.codegym.houserental.service.BookingService;
import vn.codegym.houserental.utils.ModelMapperUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ModelMapperUtil mapperUtil;

    @PostMapping
    public ResponseEntity<?> booking(@RequestBody BookingRequest request) {
        try {
            return new ResponseEntity<>(bookingService.save(request), HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<BookingDTO>> showAllByUserId(@PathVariable Long userId, boolean deleteFlag) {
        List<Booking> bookings = (List<Booking>) bookingService.findAllByUserId(userId,false);
        if (bookings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
    }
    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<BookingDTO>> showAllByHouseId(@PathVariable Long houseId) {
        List<Booking> bookings = (List<Booking>) bookingService.findAllByHouseId(houseId,false);
        if (bookings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(bookings, BookingDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<DeleteHouseResponse> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(new DeleteHouseResponse("MS-B2-01"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new DeleteHouseResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }

}
