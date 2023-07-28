package kz.berekebank.qrauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenerateQrRq {
    private String pcId;
    private String username;
    private Long creationTime;
}
