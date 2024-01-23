package vn.codegym.houserental.dto;

import lombok.Data;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.account.User;

import java.time.LocalDateTime;

@Data
public class WishListDTO {
    private Long id;
    private UserDTO user;
    private HouseDTO house;
    private LocalDateTime createdAt;
}
