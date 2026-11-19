<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Book Catalogue | LIBIS</title>
</head>

<body>

<!-- header -->
<jsp:include page="/WEB-INF/views/fragments/header.jspf"/>

<main class="container py-5">

    <section class="catalogue-header shadow-sm p-4 mb-4">
        <h1 class="h3 mb-2">Book Catalogue</h1>

        <p class="text-muted mb-3">
            Discover books available in the library.
        </p>

        <form action="${pageContext.request.contextPath}/book" method="get">
            <input type="hidden" name="action" value="searchBook">
            <div class="input-group">
                <input
                        type="search"
                        name="searchTerm"
                        class="form-control"
                        placeholder="Search by book title or author..."
                        aria-label="Search by book title or author"
                        value="<c:out value='${searchTerm}'/>">

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

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/fragments/footer.jspf"/>

</body>
</html>