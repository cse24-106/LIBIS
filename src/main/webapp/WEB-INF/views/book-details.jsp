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

<nav class="libis-navbar">

    <div class="container">

        <div class="d-flex
                    justify-content-between
                    align-items-center">

            <a href="${pageContext.request.contextPath}/book"
               class="brand">

                LIBIS

            </a>


            <a href="${pageContext.request.contextPath}/login-servlet?action=logout"
                class="btn logout-btn">

                Logout
            </a>

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