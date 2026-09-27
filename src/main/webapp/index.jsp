<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Report Lost & Found</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary shadow-sm mb-4">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🎒 Lost & Found Campus Portal</a>
            <a href="login.jsp" class="btn btn-outline-light btn-sm">Admin Dashboard</a>
        </div>
    </nav>

    <div class="container" style="max-width: 600px;">
        <div class="card p-4 shadow-sm">
            <h4 class="mb-3 text-secondary">Submit a New Item Record</h4>

            <%-- Alert Banner for Success/Fail messages --%>
            <% if (session.getAttribute("message") != null) { %>
                <div class="alert alert-<%= session.getAttribute("messageType") %> alert-dismissible fade show">
                    <%= session.getAttribute("message") %>
                </div>
                <%
                    session.removeAttribute("message");
                    session.removeAttribute("messageType");
                %>
            <% } %>

            <form action="ItemServlet" method="POST">
                <div class="mb-3">
                    <label class="form-label">Item Name</label>
                    <input type="text" name="itemName" class="form-control" placeholder="e.g., Casio Calculator, Blue Wallet" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Description / Location Found</label>
                    <textarea name="description" class="form-control" rows="3" placeholder="Provide details like colors, markings, or room numbers..." required></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Category Classification</label>
                    <select name="category" class="form-select" required>
                        <option value="" disabled selected>Select status...</option>
                        <option value="Lost">I Lost This Item</option>
                        <option value="Found">I Found This Item</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success w-100 py-2">Submit Report</button>
            </form>
        </div>
    </div>
</body>
</html>