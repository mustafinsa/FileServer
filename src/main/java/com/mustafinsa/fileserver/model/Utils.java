package com.mustafinsa.fileserver.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

    public static String getFileName(File file) {
        return file.toString().substring(file.toString().lastIndexOf("\\") + 1);
    }


    public static Map<String, File> getFiles() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties");

        File path = new File(resourceBundle.getString("path"));

        Map<String, File> result = new ConcurrentHashMap<>();

        for (File file : path.listFiles()) {
            if (file.isFile()) {
                result.put(checkMD5(file), file);
            }
        }
        return result;
    }

    public static String checkMD5(File file) {
        String md5 = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
