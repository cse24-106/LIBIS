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