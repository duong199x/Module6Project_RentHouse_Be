package org.example.projectmodule6renthousebe.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.example.projectmodule6renthousebe.model.Category;
import org.example.projectmodule6renthousebe.model.Convenient;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
