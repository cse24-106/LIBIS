<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title><c:out value="${book.title}"/> | LIBIS</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/src/main/webapp/css/style.css">

</head>

<body class="libis-page">

<!-- nav bar -->
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

<div class="book-page">

    <div class="book-details-card">

        <!-- Book Title -->
        <h1 class="book-title">
            ${book.title}
        </h1>


        <!-- Book Information -->
        <div class="book-info">

            <div class="book-info-item">

                <span class="book-info-label">
                    Book ID
                </span>

                <span class="book-info-value">
                    ${book.book_Id}
                </span>

            </div>


            <div class="book-info-item">

                <span class="book-info-label">
                    Author
                </span>

                <span class="book-info-value">
                    ${book.author}
                </span>

            </div>


            <div class="book-info-item">

                <span class="book-info-label">
                    Genre
                </span>

                <span class="book-info-value">
                    ${book.genre}
                </span>

            </div>


            <div class="book-info-item">

                <span class="book-info-label">
                    Status
                </span>

                <span class="book-info-value">

                    <c:choose>

                        <c:when test="${book.available}">

                            <span class="book-status available">
                                Available
                            </span>

                        </c:when>

                        <c:otherwise>

                            <span class="book-status unavailable">
                                Unavailable
                            </span>

                        </c:otherwise>

                    </c:choose>

                </span>

            </div>

        </div>


        <!-- Synopsis -->
        <div class="book-synopsis">

            <h2>
                Synopsis
            </h2>

            <p>
                ${book.synopsis}
            </p>

        </div>


        <!-- Back to Library -->
        <a class="back-link"
           href="${pageContext.request.contextPath}/book">

            ← Back to Library

        </a>

    </div>

</div>

<!-- FOOTER-->

<footer class="libis-footer">

    © 2026 LIBIS · Library Information System

</footer>

</body>

</html>