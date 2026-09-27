<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.lostfound.model.Item" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Managed Items</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark shadow-sm mb-4">
        <div class="container">
            <span class="navbar-brand mb-0 h1">🔐 Admin Management Console</span>
            <span class="navbar-text text-white me-3">Logged in as: <strong><%= session.getAttribute("currentUser") %></strong></span>
            <a href="LogoutServlet" class="btn btn-danger btn-sm">Secure Logout</a>
        </div>
    </nav>

    <div class="container-fluid px-4">
        <% if (session.getAttribute("message") != null) { %>
            <div class="alert alert-<%= session.getAttribute("messageType") %>">
                <%= session.getAttribute("message") %>
            </div>
            <%
                session.removeAttribute("message");
                session.removeAttribute("messageType");
            %>
        <% } %>

        <div class="card shadow-sm">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 text-dark">Active Campus Database Records</h5>
            </div>
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>DB ID</th>
                            <th>Item Name</th>
                            <th>Detailed Description</th>
                            <th>Status Classification</th>
                            <th>Timestamp Logged</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Item> items = (List<Item>) request.getAttribute("items");
                            if (items != null && !items.isEmpty()) {
                                for (Item item : items) {
                        %>
                        <tr>
                            <td><strong>#<%= item.getId() %></strong></td>
                            <td class="fw-bold"><%= item.getItemName() %></td>
                            <td><%= item.getDescription() %></td>
                            <td>
                                <span class="badge <%= item.getCategory().equalsIgnoreCase("Lost") ? "bg-danger" : "bg-success" %>">
                                    <%= item.getCategory() %>
                                </span>
                            </td>
                            <td class="text-muted small"><%= item.getDateAdded() %></td>
                            <td class="text-center">
                                <a href="ItemServlet?action=delete&id=<%= item.getId() %>"
                                   class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Are you sure you want to resolve and delete this item?');">
                                   Resolve & Clear
                                </a>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="6" class="text-center py-4 text-muted">No records found in the database.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>