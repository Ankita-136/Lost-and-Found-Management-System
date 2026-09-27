package com.lostfound.controller;

import com.lostfound.dao.ItemDAO;
import com.lostfound.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ItemServlet")
public class ItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the active session or create one to store success/fail messages
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "delete":
                // SECURE: Only logged-in users/admins can delete item records
                if (session.getAttribute("currentUser") == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }

                int id = Integer.parseInt(request.getParameter("id"));
                if (itemDAO.deleteItem(id)) {
                    session.setAttribute("message", "Item record removed successfully!");
                    session.setAttribute("messageType", "warning");
                } else {
                    session.setAttribute("message", "Failed to delete item record.");
                    session.setAttribute("messageType", "danger");
                }
                response.sendRedirect("ItemServlet?action=list");
                break;

            case "list":
            default:
                // SECURE: Only logged-in users/admins can view the dashboard database entries
                if (session.getAttribute("currentUser") == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }

                // Fetch up-to-date data from MySQL database via DAO
                List<Item> items = itemDAO.getAllItems();
                request.setAttribute("items", items);
                request.getRequestDispatcher("viewItems.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Use request.getSession() instead of false so it handles message updates safely
        HttpSession session = request.getSession();

        // 1. Extract Form Data parameters from the view (Publicly accessible)
        String name = request.getParameter("itemName");
        String desc = request.getParameter("description");
        String cat = request.getParameter("category");

        // 2. Map form data to the Item Model instance
        Item item = new Item();
        item.setItemName(name);
        item.setDescription(desc);
        item.setCategory(cat);

        // 3. Send it to the DAO to save in MySQL using the correct boolean method
        boolean isInserted = itemDAO.insertItem(item);

        // 4. Set UI feedback dynamically using context session attributes
        if (isInserted) {
            session.setAttribute("message", "Item reported successfully!");
            session.setAttribute("messageType", "success");
        } else {
            session.setAttribute("message", "Failed to report item.");
            session.setAttribute("messageType", "danger");
        }

        // 5. PRG Pattern (Post-Redirect-Get)
        // If logged in, go to dashboard. If public user, send back to home page form.
        if (session.getAttribute("currentUser") != null) {
            response.sendRedirect("ItemServlet?action=list");
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}