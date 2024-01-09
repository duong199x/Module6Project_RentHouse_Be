package org.example.projectmodule6renthousebe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.projectmodule6renthousebe.model.account.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private int bedRoom;
    @Column(nullable = false)
    private int bathRoom;
    @Column(nullable = false)
    private int livingRoom;
    @Column(nullable = false)
    private int kitchen;


    @ManyToOne
    private Category category;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "house_convenient",
            joinColumns = {
                    @JoinColumn(name = "house_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "convenient_id", referencedColumnName = "id")
            }
    )
    private Set<Convenient> convenients;

    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;
    @ManyToOne
    private User user;

}
