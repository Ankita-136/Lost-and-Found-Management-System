<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>LFMS - Admin Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow-sm" style="width: 24rem;">
            <h3 class="text-center text-primary mb-4">LFMS Admin Login</h3>

            <%-- Display error if login fails --%>
            <% if (session.getAttribute("loginError") != null) { %>
                <div class="alert alert-danger p-2 small text-center">
                    <%= session.getAttribute("loginError") %>
                </div>
                <% session.removeAttribute("loginError"); %>
            <% } %>

            <form action="LoginServlet" method="POST">
                <div class="mb-3">
                    <label class="form-label">User ID</label>
                    <input type="text" name="userId" class="form-control" placeholder="e.g., admin" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" placeholder="••••••••" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Sign In</button>
            </form>
            <div class="text-center mt-3">
                <a href="index.jsp" class="text-decoration-none small">← Back to Public Report Form</a>
            </div>
        </div>
    </div>
</body>
</html>