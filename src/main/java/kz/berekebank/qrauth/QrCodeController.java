package kz.berekebank.qrauth;

import kz.berekebank.qrauth.dto.GenerateQrRq;
import kz.berekebank.qrauth.usecase.CheckKeyUseCase;
import kz.berekebank.qrauth.usecase.GenerateQrUseCase;
import kz.berekebank.qrauth.utils.QrCheckRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
@Slf4j
public class QrCodeController {

    private final GenerateQrUseCase generateQrUseCase;
    private final CheckKeyUseCase checkKeyUseCase;

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generate(@RequestBody GenerateQrRq reqData) {
        try {
            return generateQrUseCase.generate(reqData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody QrCheckRq rq) throws Exception {
        return checkKeyUseCase.execute(rq.getKey());
    }

}
