package com.el.canno.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

/**
 * User Canno
 * Date 2018/1/2
 * Time 11:38
 */
public class QRCodeUtils {
    private static Logger LOG = LoggerFactory.getLogger(QRCodeUtils.class);

    public static void QRCodeEncode(String content, String filePath) {
        // 生成图像属性
        int width = 300;
        int height = 300;
        String format = "png";

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 生成矩阵
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // 输出图像
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, format, FileSystems.getDefault().getPath(filePath));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        LOG.info("QRCode success:{}", filePath);
    }

    public static void QRCodeDecode(String filePath) {
        ;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            // 对图像进行解码
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);

            LOG.info("QRCode content :{}", result.getText());
            LOG.info("QRCode encode :{}", result.getBarcodeFormat());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (NotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        String content = "weixin://wxpay/bizpayurl?pr=dV8oozg";
        String filePath = "d:/2017.png";
        QRCodeEncode(content, filePath);
        QRCodeDecode(filePath);
    }
}
