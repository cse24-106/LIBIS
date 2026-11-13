<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>LIBIS - Login Failed</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container mt-5" style="max-width: 450px;">
    <div class="card border-danger shadow-sm">
        <div class="card-body p-4 text-center">
            <h4 class="text-danger mb-3">Authentication Failed</h4>
            <p class="text-muted">Invalid username or password. Please check your credentials and try again.</p>
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-primary mt-2">Try Again</a>
        </div>
    </div>
</div>
</body>
</html>