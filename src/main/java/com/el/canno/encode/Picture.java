package com.el.canno.encode;

import com.el.canno.common.encrypt.MD5Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * User Canno
 * Date 2018/2/23
 * Time 17:59
 */
public class Picture {
    private String savePic(MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(content));
            if (image == null) {
                throw new RuntimeException("文件格式不正确！");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File f = new File("", MD5Utils.md5(content));
        try (OutputStream os = new FileOutputStream(f)) {
            os.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return f.getName();
    }
}
