package vn.codegym.houserental.dto;

import lombok.Data;

@Data
public class JwtDTO {
    private Long id;
    private String accessToken;
}
