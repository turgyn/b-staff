package kz.berekebank.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditProfileRequest {
    private String phoneNumber;
    private String fullName;
    private String password;
    private String newPassword;
}
