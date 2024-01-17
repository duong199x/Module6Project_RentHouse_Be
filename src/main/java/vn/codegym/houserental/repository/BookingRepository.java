package vn.codegym.houserental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.House;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
    Iterable<Booking> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag);


}