package com.mustafinsa.fileserver.controller;

import com.mustafinsa.fileserver.model.FileDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SearchController extends HttpServlet{
    private FileDAO fileDAO = FileDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("search");
        if (searchString == null || searchString.isEmpty()) {
            req.setAttribute("message", "Name cannot be empty");
            req.getRequestDispatcher("message").forward(req, resp);
        } else {
            Map<String, String> searchMap = fileDAO.searchByName(searchString);
            req.setAttribute("search_result", searchMap);
            req.getRequestDispatcher("search_result.jsp").forward(req, resp);
        }
    }

}
