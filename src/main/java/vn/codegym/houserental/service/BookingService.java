package vn.codegym.houserental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.BookingStatus;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.HouseStatus;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.repository.BookingRepository;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.repository.UserRepository;
import vn.codegym.houserental.requests.BookingRequest;
import vn.codegym.houserental.service.impl.HouseServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private HouseServiceImpl houseService;

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
        booking.setCreateAt(LocalDateTime.now());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setHouse(house);
        booking.setUser(user);
        booking.setStatus(BookingStatus.IN_PROGRESS);
        house.setStatus(HouseStatus.PREBOOK);
        houseService.save(house);
        return bookingRepository.save(booking);
    }

    public Iterable<Booking> findAllByUserId(Long userId,boolean deleteFlag){
        return bookingRepository.findAllByUserIdAndDeleteFlag(userId,deleteFlag);
    }

    public void cancelBooking(Long idBooking) {
        Optional<Booking> booking = bookingRepository.findById(idBooking);
        if (booking.isPresent()) {
            Date currentDate = new Date();
            Date bookingDate = booking.get().getStartDate();
            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate bookingLocalDate = bookingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (currentLocalDate.isBefore(bookingLocalDate.minusDays(1))) {
                booking.get().setDeleteFlag(true);
                bookingRepository.save(booking.get());
                House house = booking.get().getHouse();
                house.setStatus(HouseStatus.AVAILABLE);
                houseService.save(house);
            } else {
                throw new RuntimeException("Booking cannot be canceled. It's too close to the check-in date.");
            }
        }
    }
    }
