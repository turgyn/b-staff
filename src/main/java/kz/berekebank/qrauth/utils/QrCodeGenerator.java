package kz.berekebank.qrauth.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class QrCodeGenerator {

    // dead code
    private static final String LOGO_PATH = "";

    public static byte[] generateQRCode(String keyData) throws WriterException, IOException {
        int width = 300; // Adjust the width of the QR code image
        int height = 300; // Adjust the height of the QR code image

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(keyData, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    // dead code
    private byte[] addLogo(BitMatrix qrBitMatrix) throws IOException {
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(qrBitMatrix);

        // Read the logo file
        BufferedImage logoImage = ImageIO.read(new File(LOGO_PATH));
        // Calculate the delta height and width between QR code and logo
        int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
        int deltaWidth = qrImage.getWidth() - logoImage.getWidth();

        // Initialize combined image
        BufferedImage combinedImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combinedImage.getGraphics();

        // Write QR code to new image at position 0/0
        g.drawImage(qrImage, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Write logo into combine image at position (deltaWidth / 2) and
        // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
        // the same space for the logo to be centered
        g.drawImage(logoImage, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(combinedImage, "PNG", outputStream);

        return outputStream.toByteArray();
    }

}
