package org.example.projectmodule6renthousebe.requests;

import lombok.Data;

@Data
public class PasswordRequest {
    private String oldPassword;
    private String password;
    private String confirmPassword;
}