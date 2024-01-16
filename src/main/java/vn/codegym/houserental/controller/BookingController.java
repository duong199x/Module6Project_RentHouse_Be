package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.requests.BookingRequest;
import vn.codegym.houserental.response.ApiResponse;
import vn.codegym.houserental.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

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

}
