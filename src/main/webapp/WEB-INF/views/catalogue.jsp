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

    <title>Book Catalogue | LIBIS</title>
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

<main class="container py-5">

    <section class="catalogue-header shadow-sm p-4 mb-4">
        <h1 class="h3 mb-2">Book Catalogue</h1>

        <p class="text-muted mb-3">
            Discover books available in the library.
        </p>

        <form
                action="${pageContext.request.contextPath}/catalogue"
                method="get">

            <div class="input-group">
                <input
                        type="search"
                        name="searchTerm"
                        class="form-control"
                        placeholder="Search by book title..."
                        aria-label="Search by book title"
                        value="${searchTerm}">

                <button class="btn btn-libis" type="submit">
                    Search
                </button>
            </div>
        </form>
    </section>

    <section>
        <h2 class="h5 mb-3">Search Results</h2>

        <c:choose>
            <c:when test="${empty searchResults}">
                <div class="alert alert-warning" role="status">
                    No books matched your search.
                    Try another title.
                </div>
            </c:when>

            <c:otherwise>
                <p class="text-muted">
                    Found ${searchResults.size()} matching book(s).
                </p>

                <div class="row g-3">

                    <c:forEach var="book"
                               items="${searchResults}">

                        <div class="col-12 col-md-6 col-lg-4">
                            <article class="book-card shadow-sm p-4">

                                <div class="book-icon mb-3"
                                     aria-hidden="true">
                                    &#128214;
                                </div>

                                <h3 class="h5">
                                    <c:out value="${book.title}"/>
                                </h3>

                                <p class="text-muted small mb-0">
                                    Library catalogue
                                </p>

                            </article>
                        </div>

                    </c:forEach>

                </div>
            </c:otherwise>
        </c:choose>
    </section>

</main>

<footer class="text-center py-4 text-muted small">
    LIBIS &mdash; Library Information System
</footer>

</body>
</html>