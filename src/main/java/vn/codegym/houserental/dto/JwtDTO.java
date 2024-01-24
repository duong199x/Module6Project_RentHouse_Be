package vn.codegym.houserental.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import vn.codegym.houserental.model.account.Role;

import java.util.Collection;

@Data
public class JwtDTO {
    private Long id;
    private String accessToken;
    private Collection<? extends GrantedAuthority> roles;
}
