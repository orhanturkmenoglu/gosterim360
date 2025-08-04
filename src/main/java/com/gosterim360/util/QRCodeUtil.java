package com.gosterim360.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

   public static byte[]  generateQRCodeBytes(String content){
       try {
           int width = 300;
           int height = 300;
           QRCodeWriter qrCodeWriter = new QRCodeWriter();

           Map<EncodeHintType, Object> hints = new HashMap<>();
           hints.put(EncodeHintType.MARGIN, 1); // Daha az boşluk

           BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

           BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           ImageIO.write(bufferedImage, "png", outputStream);
           return outputStream.toByteArray();

       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }
}
