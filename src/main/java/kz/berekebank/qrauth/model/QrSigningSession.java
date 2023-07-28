package kz.berekebank.qrauth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class QrSigningSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDateTime qrCreationTime;
    private LocalDateTime qrSigningTime;
    private String item;

    public QrSigningSession(Long userId, LocalDateTime qrCreationTime, LocalDateTime qrSigningTime, String item) {
        this.userId = userId;
        this.qrCreationTime = qrCreationTime;
        this.qrSigningTime = qrSigningTime;
        this.item = item;
    }
}
