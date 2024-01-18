package vn.codegym.houserental.cronjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.BookingStatus;
import vn.codegym.houserental.repository.BookingRepository;

import java.lang.annotation.*;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTask {
    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(cron = "0 1 7 * * *", zone = "GMT+7")
    public void checkoutSchedule() {
        Iterable<Booking> bookings = bookingRepository.findAllByStatusAndDeleteFlag(BookingStatus.CHECK_IN, false);
        for (Booking booking : bookings) {
            System.out.println(booking.getEndDate());
            System.out.println(new Date());
            if (booking.getEndDate().before(new Date())) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingRepository.save(booking);
            }
        }
    }
}
