package com.mustafinsa.fileserver.controller;

import com.mustafinsa.fileserver.model.FileDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SearchController extends HttpServlet{
    private FileDao fileDao = FileDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchString = req.getParameter("search");
        if (searchString == null || searchString.isEmpty()) {
            req.setAttribute("message", "Name cannot be empty");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        } else {
            Map<String, String> searchMap = fileDao.searchByName(searchString);
            req.setAttribute("search_result", searchMap);
            req.getRequestDispatcher("search_result.jsp").forward(req, resp);
        }
    }

}
