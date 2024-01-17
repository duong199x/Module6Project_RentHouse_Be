package vn.codegym.houserental.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.BookingStatus;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.repository.BookingRepository;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.repository.UserRepository;
import vn.codegym.houserental.requests.BookingRequest;

import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    public Booking save(BookingRequest request) throws CommonException {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new CommonException("Ngày đặt phòng không được để trống");
        }
        Booking booking = new Booking();

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
        House house = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));

        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setPrice(request.getPrice());
        booking.setCreateAt(request.getCreateAt());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setHouse(house);
        booking.setUser(user);
        booking.setStatus(BookingStatus.IN_PROGRESS);
        return bookingRepository.save(booking);
    }

}
