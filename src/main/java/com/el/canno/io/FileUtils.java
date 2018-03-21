package com.el.canno.io;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.zip.*;

/**
 * User Canno
 * Date 2018/1/4
 * Time 9:29
 */
public class FileUtils {
    /**
     * 读取文件 返回对应编码字符串
     * 默认 编译器编码
     *
     * @param filePath
     * @param encode
     * @return
     * @throws IOException
     */
    public static String fileRead(String filePath, String encode) throws IOException {
        InputStream in = new FileInputStream(new File(filePath));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = -1;
        while ((i = in.read()) != -1) {
            byteArrayOutputStream.write(i);
        }

        if (StringUtils.isEmpty(encode)) {
            return new String(byteArrayOutputStream.toByteArray());
        } else {
            return new String(byteArrayOutputStream.toByteArray(), encode);
        }
    }

    public static byte[] fileRead(String filePath) throws IOException {
        InputStream in = new FileInputStream(new File(filePath));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i = -1;
        while ((i = in.read()) != -1) {
            byteArrayOutputStream.write(i);
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 写文件
     *
     * @param content
     * @param filePath
     * @param encode
     * @throws IOException
     */
    public static void fileWrite(String content, String filePath, String encode) throws IOException {
        File file = new File(filePath);

        //文件超过某个大小 生成新的文件
//        File file1 = new File("d:/test/2018.txt");
//        if(file.exists() && file.length() > 1024){
//            boolean isSucc = file.renameTo(file1);
//            System.out.println(isSucc);
//            file.createNewFile();
//        }

        FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath), true);
        fileOutputStream.write((System.currentTimeMillis() + "\r\n").getBytes());
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }

    /**
     * 文件压缩
     *
     * @param filePath
     * @param zipFileName
     * @throws IOException
     */
    public static void fileZip(String filePath, String zipFileName) throws IOException {
        File file = new File(filePath);
        int bufferSize = 1024;
        long length = file.length();
        if (length < 1024 * 1024) {
            bufferSize = (int) length;
        } else {
            bufferSize = 1024 * 1024;
        }

        System.out.println(bufferSize);
        // 对输出文件做CRC32校验
        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                new File(zipFileName)), new CRC32());

        ZipOutputStream zos = new ZipOutputStream(cos);
        ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());

        zos.putNextEntry(zipEntry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                filePath));
        int count;
        byte data[] = new byte[bufferSize];
        while ((count = bis.read(data, 0, bufferSize)) != -1) {
            zos.write(data, 0, count);
        }
        //   zipEntry.setMethod(ZipEntry.STORED);
        bis.close();
        zos.closeEntry();
        zos.close();
    }

    /**
     * 文件解压
     *
     * @param filePath
     * @param newFilePath
     * @throws IOException
     */
    public static void fileUnzip(String filePath, String newFilePath) throws IOException {
        int bufferSize = 1024;
        CheckedInputStream ins = new CheckedInputStream(new FileInputStream(
                new File(filePath)), new CRC32());
        ZipInputStream zipInputStream = new ZipInputStream(ins);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 表明是解压
        // zipEntry.setMethod(ZipEntry.STORED);
        File file = new File(newFilePath + "/" + zipEntry.getName());

        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        System.out.println(zipEntry.getName());
        int count;
        byte data[] = new byte[bufferSize];
        while ((count = zipInputStream.read(data, 0, bufferSize)) != -1) {
            fileOutputStream.write(data, 0, count);
        }

        fileOutputStream.close();
    }

    public static boolean fileCopy(String filePath, String newFilePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        String extensionName = "";
        String name = fileName;
        if (fileName.contains(".")) {
            extensionName = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            name = fileName.substring(0, fileName.length() - extensionName.length());
        }
        File copyFile = new File(file.getParent() + "/" + name + "bk" + extensionName);
        FileOutputStream fileOutputStream = new FileOutputStream(copyFile);
        BufferedInputStream ins = new BufferedInputStream(new FileInputStream(file));
        byte data[] = new byte[1024];
        while (ins.read(data, 0, 1024) != -1) {
            fileOutputStream.write(data, 0, 1024);
        }
        fileOutputStream.flush();
        fileOutputStream.close();

        return true;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println((-1) | 256);
//        System.out.println(255 & 255);
//        int r = 0;
//        int c1 = 0;
//        int c2 = 0;
//        long t1 = System.currentTimeMillis();
//        for (; ; ) {
//            r = 99999999%256;
//            c1++;
//            if (c1 > 2 << 30)
//                break;
//        }
//
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2 - t1);
//        for (; ; ) {
//            r = 99999999 & 256;
//            c2++;
//            if (c2 > 2 << 30)
//                break;
//        }
//        System.out.println(c1 );
//        long t3 = System.currentTimeMillis();
//        System.out.println(t3 - t2);

//
//        System.out.println("_______________________________");
//        System.out.println((((2551 & 256) == 256) ? 2555 & 256 : 2555 & 256));
//        System.out.println((2551 & 255) >>>8);
//        System.out.println((2551 % 255));
//        System.out.println(2^1);

        System.out.println(Integer.toBinaryString(500 & 255));
        System.out.println(Integer.toBinaryString(500 % 256));
        System.out.println(Integer.toBinaryString(500));
        System.out.println(Integer.toBinaryString(255));
    }
}
