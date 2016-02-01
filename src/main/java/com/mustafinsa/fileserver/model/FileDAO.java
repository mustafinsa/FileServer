package com.mustafinsa.fileserver.model;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileDao {
    private Map<String, File> files = Utils.getFiles();
    private static FileDao ourInstance = new FileDao();

    public static FileDao getInstance() {
        return ourInstance;
    }

    private FileDao() {
    }

    public File getFileById(String id) {
        return files.get(id);
    }

    public boolean saveFile(FileItem fileItem, File file) {
        boolean result = false;
        try {
            fileItem.write(file);
            if (!files.containsKey(Utils.checkMD5(file))) {
                //update map
                files = Utils.getFiles();
                result = true;
            } else {
                file.delete();
            }
        } catch (Exception e) {
            files = Utils.getFiles();
            e.printStackTrace();
        }

        return result;
    }

    public Map<String, String> searchByName(String name) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, File> pair : files.entrySet()) {
            String fileName = Utils.getFileName(pair.getValue());
            if (fileName.contains(name)) {
                result.put(pair.getKey(), fileName);
            }
            if (result.size() == 25) {
                break;
            }
        }
        return result;
    }

    public String deleteById(String id) {
        String result = null;
        File file = files.get(id);
        if (file != null) {
            if (file.delete()) {
                files.remove(id);
                result = "File deleted successfully!";
            } else {
                result = "Some error with remove";
            }
        } else {
            result = "Id didn't find";
        }
        return result;
    }
}
