package org.zcw.staticsframework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lenovo on 2017/7/14.
 */

public class FileUtils {
    //保存路径\n"
     public static final String PATH = "/sdcard/applog/" ;

    /**
     * 删除指定路径的文件
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        try {
            if (filePath == null) {
                return false;
            }
            File file = new File(filePath);
            if (file!=null&&file.exists()) {
                file.delete();
                return  true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        return  true;
    }

    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     /**
         * 删除目录及目录下的文件
         *
         * @param dir
         *            要删除的目录的文件路径
         * @return 目录删除成功返回true，否则返回false
         */
        public static boolean deleteDirectory(String dir) {
            // 如果dir不以文件分隔符结尾，自动添加文件分隔符
            if (!dir.endsWith(File.separator))
                dir = dir + File.separator;
            File dirFile = new File(dir);
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
                LogUtils.printLogSD(LogUtils.ERROR,"删除目录失败：" + dir + "不存在！");
                return false;
            }
            boolean flag = true;
            // 删除文件夹中的所有文件包括子目录
            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag)
                        break;
                }
                // 删除子目录
                else if (files[i].isDirectory()) {
                    flag = deleteDirectory(files[i]
                            .getAbsolutePath());
                    if (!flag)
                        break;
                }
            }
            if (!flag) {
                LogUtils.printLogSD(LogUtils.ERROR,"删除目录失败！");
                return false;
            }
            // 删除当前目录
            if (dirFile.delete()) {
                LogUtils.printLogSD(LogUtils.ERROR,"删除目录" + dir + "成功！");
                return true;
            } else {
                return false;
            }
        }


    private static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

     MessageDigest messageDigest = null;


    /**
     * 计算文件的MD5
     * @param fileName 文件的绝对路径
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(String fileName) throws IOException {
        File f = new File(fileName);
        return getMd5ByFile(f);
    }


    public static String getMd5ByFile(File file) {
        InputStream fis;
        byte[] buffer = new byte[2048];
        int numRead = 0;
        MessageDigest md5;

        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return md5ToString(md5.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    public static String md5ToString(byte[] md5Bytes) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    /**
     * 计算文件的MD5，重载方法
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
       MessageDigest messageDigest=null;
        try{
            messageDigest = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e) {
            System.err.println(FileUtils.class.getName()+"初始化失败，MessageDigest不支持MD5Util.");
            e.printStackTrace();
        }

        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messageDigest.update(byteBuffer);
        in.close();
        ch.close();
        file=null;
//        System.gc();
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
