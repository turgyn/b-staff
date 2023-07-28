package kz.berekebank.qrauth.repository;

import kz.berekebank.qrauth.model.QrSigningSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrSigningSesssionRepository extends JpaRepository<QrSigningSession, Long> {
}
