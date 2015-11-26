package com.mustafinsa.fileserver.controller;

import com.mustafinsa.fileserver.model.FileDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class UploadController extends HttpServlet {
    private FileDAO fileDAO = FileDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties");
        String savePath = resourceBundle.getString("path");

        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        fileFactory.setRepository(new File(savePath));
        ServletFileUpload uploader = new ServletFileUpload(fileFactory);

        try {
            List<FileItem> fileItemsList = uploader.parseRequest(req);
            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
            while (fileItemsIterator.hasNext()) {
                FileItem fileItem = fileItemsIterator.next();
                String fileName = fileItem.getName();
                File file = new File(savePath + File.separator + fileItem.getName());
                boolean isSave = fileDAO.saveFile(fileItem, file);
                if (isSave) {
                    if (fileName != null && !fileName.isEmpty()) {
                        req.setAttribute("message", fileName + " file uploaded successfully!");
                    } else {
                        req.setAttribute("message", "File not found.");
                    }
                } else {
                    req.setAttribute("message", "File already exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("message.jsp").forward(req, resp);
    }

}
