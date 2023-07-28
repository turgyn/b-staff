package kz.berekebank.qrauth.usecase;

import kz.berekebank.qrauth.dto.GenerateQrRq;
import kz.berekebank.qrauth.utils.Encryptor;
import kz.berekebank.qrauth.utils.QrCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenerateQrUseCase {

    public byte[] generate(GenerateQrRq reqData) throws Exception {
//        String text = getStringValue(reqData);
        String encryptedCode = Encryptor.encrypt(reqData);
        log.info("Code: " + encryptedCode);
        return QrCodeGenerator.generateQRCode(encryptedCode);
    }

    private String getStringValue(GenerateQrRq reqData) {
        String delimeter = "**%del#$";
        return reqData.getPcId() + delimeter + reqData.getUsername();
    }
}
