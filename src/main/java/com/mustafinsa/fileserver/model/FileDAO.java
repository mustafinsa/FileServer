package com.mustafinsa.fileserver.model;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileDAO {
    private Map<Integer, File> files = Utils.getFiles();
    private static FileDAO ourInstance = new FileDAO();

    public static FileDAO getInstance() {
        return ourInstance;
    }

    private FileDAO() {}

    public File getFileById(int id) {
        return files.get(id);
    }

    public boolean saveFile(FileItem fileItem, File file) {
        boolean result = false;
        if (!files.containsKey(file.hashCode())) {
            try {
                fileItem.write(file);
                //update map
                files = Utils.getFiles();
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<Integer, String> searchByName(String name) {
        Map<Integer, String> result = new HashMap<>();
        for (Map.Entry<Integer, File> pair : files.entrySet()) {
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

    public String deleteById(int id) {
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
