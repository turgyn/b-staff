package kz.berekebank.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionRs {
    private Long id;
    private String username;
    private String fullName;
    private Long userId;
    private LocalDateTime qrCreationTime;
    private LocalDateTime qrSigningTime;
    private String item;
}
