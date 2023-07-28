package kz.berekebank.admin;

import kz.berekebank.admin.dto.SessionRs;
import kz.berekebank.admin.dto.SessionsListRs;
import kz.berekebank.auth.user.User;
import kz.berekebank.auth.user.UserRepository;
import kz.berekebank.qrauth.model.QrSigningSession;
import kz.berekebank.qrauth.repository.QrSigningSesssionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final QrSigningSesssionRepository qrSigningSesssionRepository;
    private final UserRepository userRepository;

    @GetMapping("/sessions")
    public SessionsListRs getSessions(@RequestParam(required = false) Long filterUserId) {

        List<QrSigningSession> sessions = qrSigningSesssionRepository.findAll();
        List<SessionRs> sessionDtos = sessions.stream().filter(session -> {
            if (filterUserId == null) return true;
            return filterUserId.equals(session.getUserId());
        }).map(session -> {
            Long userId = session.getUserId();
            User user = userRepository.findById(userId).get();
            return new SessionRs(session.getId(), user.getPhoneNumber(), user.getFullName(), user.getId(), session.getQrCreationTime(), session.getQrSigningTime(), session.getItem());
        }).collect(Collectors.toList());
        return new SessionsListRs(sessionDtos);
    }
}
