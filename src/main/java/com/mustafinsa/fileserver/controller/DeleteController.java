package com.mustafinsa.fileserver.controller;

import com.mustafinsa.fileserver.model.FileDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteController extends HttpServlet {
    private FileDAO fileDAO = FileDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String delete_id = req.getParameter("delete_id");

        String status = fileDAO.deleteById(delete_id);

        req.setAttribute("message", status);

        req.getRequestDispatcher("message.jsp").forward(req, resp);
    }
}
