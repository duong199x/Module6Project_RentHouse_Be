package vn.codegym.houserental.model.account;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "userTable")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "[a-zA-Z0-9_.]*")
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;
    @Column(nullable = false, unique = true)
    @Email(message = "Email không hợp lệ")
    private String email;
    private String fullName;
    private String address;
    private String phone;
    @Column(nullable = true)
    private Integer age;
    private String dateOfBirth;
    private String imageUser;
    @Column(columnDefinition = "int default 0")
    private int isOwner;
    private boolean enabled;
    private String verificationToken;
    private Date verificationTokenExpiryDate;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteFlag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
}
