package com.mustafinsa.fileserver.controller;

import com.mustafinsa.fileserver.model.FileDao;
import com.mustafinsa.fileserver.model.Utils;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloadController extends HttpServlet {
    private FileDao fileDao = FileDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String download_id = req.getParameter("download_id");

        File file = fileDao.getFileById(download_id);
        if (file != null) {
            boolean flag = downloadService(resp, file);
            if (flag) {
                req.setAttribute("message", "File downloaded at client successfully");
            } else {
                req.setAttribute("message", "IOException");
            }
        } else {
            req.setAttribute("message", "Id didn't find");
        }
        req.getRequestDispatcher("message.jsp").forward(req, resp);
    }

    private boolean downloadService(HttpServletResponse resp, File file) {
        boolean flag = false;
        try {
            InputStream fis = new FileInputStream(file);

            resp.setContentType("application/download");
            resp.setContentLength((int) file.length());
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + Utils.getFileName(file) + "\"");

            ServletOutputStream os = resp.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
            flag = true;
        } catch (IOException e) {/*NOP*/}
        return flag;
    }

}
