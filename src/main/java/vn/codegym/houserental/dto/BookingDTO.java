package vn.codegym.houserental.dto;

import jakarta.persistence.*;
import lombok.Data;
import vn.codegym.houserental.model.BookingStatus;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.HouseStatus;
import vn.codegym.houserental.model.account.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingDTO {
    private Long id;
    private LocalDateTime createAt;
    private Date startDate;
    private Date endDate;
    private Integer numberOfGuests;
    private UserDTO user;
    private HouseDTO house;
    private Double price;
    private BookingStatus status;
}
