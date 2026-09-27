package com.lostfound.controller;

import com.lostfound.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // Validate user inputs using the DAO layer
        if (userDAO.validateUser(userId, password)) {
            // Login Success: Save the user identity inside the session
            session.setAttribute("currentUser", userId);

            // Redirect to the master items dashboard servlet
            response.sendRedirect("ItemServlet?action=list");
        } else {
            // Login Failure: Store error message in session and send back to login screen
            session.setAttribute("loginError", "Invalid User ID or Password!");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect accidental GET requests directly back to the login page
        response.sendRedirect("login.jsp");
    }
}