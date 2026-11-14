<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LIBIS - Book Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="book"><fmt:message key="app.title"/></a>
        <div class="collapse navbar-collapse">

            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="loans?action=myLoans"><fmt:message key="nav.myLoans"/></a></li>

                <!-- librarian management links -->
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/librarian/members">Manage Members</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/librarian/books">Manage Books</a>
                </li>
            </ul>

            <ul class="navbar-nav ms-auto align-items-center">

                <!-- logged-in user display -->
                <li class="nav-item me-3">
                        <span class="navbar-text text-light small">
                            Logged in as: <c:out value="${pageContext.request.remoteUser}"/>
                        </span>
                </li>

                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/logout" method="GET" class="d-inline">
                        <input type="hidden" name="action" value="logout">
                        <button type="submit" class="btn btn-outline-danger btn-sm"><fmt:message key="nav.logout"/></button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Book Catalogue Management</h2>
        <a href="books?action=addForm" class="btn btn-primary">+ Add New Book</a>
    </div>

    <!-- alerts -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <c:out value="${errorMessage}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- search Bar -->
    <form action="books" method="GET" class="row g-3 mb-4">
        <input type="hidden" name="action" value="list">
        <div class="col-md-6">
            <input type="text" class="form-control" name="search" placeholder="Search by title or author..." value="<c:out value='${param.search}'/>">
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-secondary w-100">Search</button>
        </div>
    </form>

    <!-- book table -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-striped table-hover mb-0 align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Year</th>
                    <th>Status</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${books}">
                    <tr>
                        <td><c:out value="${b.isbn}"/></td>
                        <td><c:out value="${b.title}"/></td>
                        <td><c:out value="${b.author}"/></td>
                        <td><c:out value="${b.genre}"/></td>
                        <td><c:out value="${b.published_year}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${b.available}">
                                    <span class="badge bg-success">Available</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">On Loan</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-end">
                            <a href="books?action=editForm&id=${b.book_Id}" class="btn btn-sm btn-outline-primary">Edit</a>
                            <a href="books?action=delete&id=${b.book_Id}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure you want to delete <c:out value="${b.title}"/>?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty books}">
                    <tr>
                        <td colspan="7" class="text-center py-4 text-muted">No books found in the catalogue.</td>
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