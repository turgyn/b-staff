package kz.berekebank.qrauth.usecase;

import kz.berekebank.auth.AuthUtil;
import kz.berekebank.auth.user.User;
import kz.berekebank.auth.user.UserRepository;
import kz.berekebank.qrauth.repository.QrSigningSesssionRepository;
import kz.berekebank.qrauth.dto.CheckKeyRs;
import kz.berekebank.qrauth.dto.GenerateQrRq;
import kz.berekebank.qrauth.model.QrSigningSession;
import kz.berekebank.qrauth.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckKeyUseCase {

    private final QrSigningSesssionRepository sessionRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> execute(String key) throws Exception {
        if (!AuthUtil.isLogged()) {
            return AuthUtil.notAuthorized();
        }
        String requesterUsername = AuthUtil.getCurUserPhoneNumber();

        log.info(String.format("Requester username: %s", requesterUsername));
        GenerateQrRq qrKey  = Encryptor.decrypt(key);

        if (!qrKey.getUsername().equals(requesterUsername)) {
            return ResponseEntity.badRequest().body(new CheckKeyRs("Отказано!", "Похоже это не ваша учетная запись"));
        }

        long creationtimestamp = qrKey.getCreationTime();
        long cur = System.currentTimeMillis()/1000;
        long diff = cur - creationtimestamp;
        System.out.println("Created: " + creationtimestamp);
        System.out.println("Current: " + cur);
        System.out.println("Diff: " + diff + " secs");

//        if (diff > 60) {    // if qr generated 60+ mins ago, its expired
//            return ResponseEntity.badRequest().body(new CheckKeyRs("Время жизни QR-а истекло. Сгенерируите QR повторно"));
//        }
        User user = userRepository.findByPhoneNumber(requesterUsername).get();
        if (!user.getIsEnabled()) {
            return ResponseEntity.badRequest().body(new CheckKeyRs("Отказано!", "Ваша учетная запись заблокирована"));
        }


        log.info(String.format("pcId: %s, username: %s", qrKey.getPcId(), qrKey.getUsername()));
        var qrCreating = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationtimestamp*1000), TimeZone.getDefault().toZoneId());
        var qrSigning = LocalDateTime.ofInstant(Instant.ofEpochMilli(cur*1000), TimeZone.getDefault().toZoneId());
        sessionRepository.save(new QrSigningSession(user.getId(), qrCreating, qrSigning, qrKey.getPcId()));

        return ResponseEntity.ok(new CheckKeyRs("Вы аутентифицированы!"));
    }
}
