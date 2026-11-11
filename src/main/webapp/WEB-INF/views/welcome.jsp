<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/src/main/webapp/css/style.css">

    <title>LIBIS - Welcome</title>

    <!-- Bootstrap 5 -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

</head>


<body>


<!-- NAVIGATION BAR-->

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

<!-- MAIN CONTENT -->

<main class="main-container">


    <!-- Welcome -->

    <section class="welcome-section">

        <div class="welcome-label">

            LIBIS Member Portal

        </div>


        <h1 class="welcome-title">

            Welcome,
            <span>

            </span>

        </h1>


        <p class="welcome-text">

            Browse our collection and discover your next great read.

        </p>

    </section>

    <!-- search bar -->
    <section class="welcome-section">
        <div class="search-card">

            <h2 class="search-title">Find a Book</h2>

            <p class="text-muted mb-3">
                Search our catalogue by book title.
            </p>

            <form action="${pageContext.request.contextPath}/catalogue"
                  method="get">

                <div class="input-group input-group-lg">
                    <input
                            type="search"
                            class="form-control"
                            name="searchTerm"
                            placeholder="Enter a book title..."
                            aria-label="Search books by title"
                    >

                    <button class="btn search-btn text-white" type="submit">
                        Search
                    </button>
                </div>

            </form>
        </div>
    </section>

    <!-- BOOK LIBRARY -->

    <section class="library-card">


        <!-- Header -->

        <div class="library-header">

            <div>

                <h2 class="library-title">

                    Library Collection

                </h2>


                <p class="library-subtitle">

                    Browse the books currently available in LIBIS.

                </p>

            </div>


            <span class="book-count">

            </span>

        </div>



        <!-- Table -->

        <div class="table-responsive table-container">

            <table class="table libis-table">

                <thead>
                <tr>
                    <th>Book ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Actions</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="book" items="${books}">

                    <tr>

                        <!-- Book ID -->

                        <td>
                <span class="book-id">
                        ${book.bookId}
                </span>
                        </td>


                        <!-- Title -->

                        <td>

                            <c:choose>

                                <c:when test="${not empty book.title}">

                        <span class="book-title">
                            <a href="${pageContext.request.contextPath}/bookact?action=view&id=${book.bookId}">
                                    ${book.title}
                            </a>
                        </span>

                                </c:when>

                                <c:otherwise>

                        <span class="empty-value">
                            No title
                        </span>

                                </c:otherwise>

                            </c:choose>

                        </td>


                        <!-- Author -->

                        <td>

                            <c:choose>

                                <c:when test="${not empty book.author}">

                        <span class="book-author">
                                ${book.author}
                        </span>

                                </c:when>

                                <c:otherwise>

                        <span class="empty-value">
                            Unknown
                        </span>

                                </c:otherwise>

                            </c:choose>

                        </td>


                        <!-- Genre -->

                        <td>

                            <c:choose>

                                <c:when test="${not empty book.genre}">

                        <span class="genre-badge">
                                ${book.genre}
                        </span>

                                </c:when>

                                <c:otherwise>

                        <span class="empty-value">
                            Not specified
                        </span>

                                </c:otherwise>

                            </c:choose>

                        </td>


                        <!-- Actions -->

                        <td class="actions-cell">

                            <a href="${pageContext.request.contextPath}/bookact?action=borrow&id=${book.bookId}"
                               class="action-btn borrow-btn">
                                Borrow
                            </a>

                            <a href="${pageContext.request.contextPath}/bookact?action=reserve&id=${book.bookId}"
                               class="action-btn reserve-btn">
                                Reserve
                            </a>

                        </td>


                        <!-- Availability -->

                        <td>

                            <c:choose>

                                <c:when test="${book.available}">

                        <span class="availability available">
                            <span class="status-dot"></span>
                            Available
                        </span>

                                </c:when>

                                <c:otherwise>

                        <span class="availability unavailable">
                            <span class="status-dot"></span>
                            Unavailable
                        </span>

                                </c:otherwise>

                            </c:choose>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </section>

</main>



<!-- FOOTER-->

<footer class="libis-footer">

    © 2026 LIBIS · Library Information System

</footer>


<!-- Bootstrap JavaScript -->

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>

</html>