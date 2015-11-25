package com.mustafinsa.fileserver.model;

import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

    public static String getFileName(File file) {
        return file.toString().substring(file.toString().lastIndexOf("\\") + 1);
    }


    public static Map<Integer, File> getFiles() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties");

        File path = new File(resourceBundle.getString("path"));

        Map<Integer, File> result = new ConcurrentHashMap<>();

        for (File file : path.listFiles()) {
            if (file.isFile()) {
                result.put(file.hashCode(), file);
            }
        }
        return result;
    }
}
