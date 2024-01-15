package vn.codegym.houserental.requests;

import lombok.Data;
import vn.codegym.houserental.model.Category;
import vn.codegym.houserental.model.account.User;

import java.util.List;

@Data
public class CreateHouseRequest {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String location;
    private int bedRoom;
    private int bathRoom;
    private int livingRoom;
    private int kitchen;
    private User user;
    private Category category;
    private List<String> images;
    private List<Long> convenientIds;
}
