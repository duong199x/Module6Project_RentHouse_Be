package vn.codegym.houserental.dto;

import lombok.Data;
import vn.codegym.houserental.model.Category;
import vn.codegym.houserental.model.Convenient;

import java.util.Set;

@Data
public class HouseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String location;
    private Category category;
    private Set<Convenient> convenients;
    private UserDTO userDTO;
}
