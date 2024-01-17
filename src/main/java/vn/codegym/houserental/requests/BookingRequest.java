package vn.codegym.houserental.requests;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.codegym.houserental.model.BookingStatus;

import java.util.Date;

@Getter
@Setter
public class BookingRequest {
    private Date startDate;

    private Date endDate;

    private Integer numberOfGuests;

    private Long userId;

    private Long houseId;
    private Double price;
}
