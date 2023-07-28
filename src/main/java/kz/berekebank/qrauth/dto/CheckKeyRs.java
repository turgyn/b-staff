package kz.berekebank.qrauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckKeyRs {
    private String title;
    private String message;

    public CheckKeyRs(String title) {
        this.title = title;
    }
}
