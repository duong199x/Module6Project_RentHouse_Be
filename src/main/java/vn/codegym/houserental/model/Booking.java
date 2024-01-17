package vn.codegym.houserental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.codegym.houserental.model.account.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column
    private Date startDate;
    @Column
    private LocalDateTime createAt;

    @Column
    private Date endDate;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;

    private Double price;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
}
