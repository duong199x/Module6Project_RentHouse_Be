package vn.codegym.houserental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.houserental.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}