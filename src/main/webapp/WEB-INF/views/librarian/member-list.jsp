<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LIBIS - Member Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- nav bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="books?action=list">LIBIS Librarian Portal</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="books?action=list">Manage Books</a></li>
                <li class="nav-item"><a class="nav-link active" href="members?action=list">Manage Members</a></li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/logout" method="GET" class="d-inline">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-outline-danger btn-sm">Logout</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Member Management</h2>
        <a href="members?action=addForm" class="btn btn-primary">+ Add New Member</a>
    </div>

    <!-- alerts -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <c:out value="${errorMessage}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- search bar -->
    <form action="members" method="GET" class="row g-3 mb-4">
        <input type="hidden" name="action" value="list">
        <div class="col-md-6">
            <input type="text" class="form-control" name="search" placeholder="Search by name, email, or member number..." value="<c:out value='${param.search}'/>">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-secondary w-100">Search</button>
        </div>
    </form>

    <!-- members table -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-striped table-hover mb-0 align-middle">
                <thead class="table-dark">
                <tr>
                    <th>Member #</th>
                    <th>Full Name</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="m" items="${members}">
                    <tr>
                        <td><c:out value="${m.memberNumber}"/></td>
                        <td><c:out value="${m.firstName}"/> <c:out value="${m.lastName}"/></td>
                        <td><c:out value="${m.username}"/></td>
                        <td><c:out value="${m.email}"/></td>
                        <td>
                                <span class="badge ${m.role == 'librarian' ? 'bg-danger' : 'bg-info'}">
                                    <c:out value="${m.role}"/>
                                </span>
                        </td>
                        <td class="text-end">
                            <a href="members?action=editForm&id=${m.memberId}" class="btn btn-sm btn-outline-primary">Edit</a>
                            <a href="members?action=delete&id=${m.memberId}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure you want to delete member <c:out value="${m.memberNumber}"/>? Their loan history will also be removed via cascade.');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty members}">
                    <tr>
                        <td colspan="6" class="text-center py-4 text-muted">No members found.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>