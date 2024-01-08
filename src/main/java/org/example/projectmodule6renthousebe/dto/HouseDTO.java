package org.example.projectmodule6renthousebe.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Data
public class HouseDTO {
    private String name;
    private String description;
    private double price;
    private String location;
}
