<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>LIBIS - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container mt-5" style="max-width: 450px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="card-title text-center mb-4">LIBIS Library Login</h3>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">
                    <c:out value="${successMessage}"/>
                </div>
            </c:if>

            <!-- post to j_security_check with j_username and j_password -->
            <form action="j_security_check" method="POST">
                <div class="mb-3">
                    <label for="j_username" class="form-label">Username or Email</label>
                    <input type="text" class="form-control" id="j_username" name="j_username" required autofocus>
                </div>

                <div class="mb-3">
                    <label for="j_password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="j_password" name="j_password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Sign In</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>