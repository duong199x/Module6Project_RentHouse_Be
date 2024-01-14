package org.example.projectmodule6renthousebe.requests;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.projectmodule6renthousebe.model.Category;
import org.example.projectmodule6renthousebe.model.Convenient;
import org.example.projectmodule6renthousebe.model.account.User;

import java.util.List;
import java.util.Set;

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
