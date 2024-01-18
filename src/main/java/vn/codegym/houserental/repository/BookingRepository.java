package vn.codegym.houserental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.BookingStatus;
import vn.codegym.houserental.model.House;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);

    Iterable<Booking> findAllByHouseIdAndDeleteFlag(Long houseId, boolean deleteFlag);

    Iterable<Booking> findAllByStatusAndDeleteFlag(BookingStatus status, boolean deleteFlag);

    @Query("SELECT SUM(b.price) FROM Booking b " +
            "WHERE MONTH(b.createAt) = :month AND b.status = :status AND b.house.user.id = :userId AND b.deleteFlag = false ")
    Double getTotalPriceByMonthAndStatusAndUserId(@Param("month") int month,
                                                  @Param("status") BookingStatus status,
                                                  @Param("userId") Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE MONTH(b.createAt) = :month " +
            "AND b.status = :status " +
            "AND b.house.user.id = :userId " +
            "AND b.deleteFlag = false")
    Iterable<Booking> getBookingsByMonthAndStatusAndUserId(@Param("month") int month,
                                                           @Param("status") BookingStatus status,
                                                           @Param("userId") Long userId);

    @Query("SELECT b FROM Booking b JOIN b.house h WHERE h.user.id = :userId AND b.deleteFlag = false")
    Iterable<Booking> findBookingsByUserIdAndNotDeleted(@Param("userId") Long userId);




}