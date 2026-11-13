<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- handles language switching via query parameter -->
<c:if test="${not empty param.lang}">
    <fmt:setLocale value="${param.lang}" scope="session"/>
</c:if>
<fmt:bundle basename="messages">

    <!DOCTYPE html>
    <html lang="${sessionScope['javax.servlet.jsp.jstl.fmt.locale'] != null ? sessionScope['javax.servlet.jsp.jstl.fmt.locale'] : 'en'}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="app.title"/></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

    <!-- nav bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="catalogue"><fmt:message key="app.title"/></a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link active" href="catalogue"><fmt:message key="nav.catalogue"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="loans?action=myLoans"><fmt:message key="nav.myLoans"/></a></li>
                </ul>
                <ul class="navbar-nav ms-auto align-items-center">
                    <!-- Language Switch -->
                    <li class="nav-item me-3">
                        <c:choose>
                            <c:when test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale'] == 'tn'}">
                                <a class="btn btn-sm btn-outline-light" href="catalogue?lang=en">English</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-sm btn-outline-light" href="catalogue?lang=tn">Setswana</a>
                            </c:otherwise>
                        </c:choose>
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
        <h2 class="mb-4"><fmt:message key="catalogue.heading"/></h2>

        <!-- Search Form -->
        <form action="catalogue" method="GET" class="row g-3 mb-4">
            <div class="col-md-6">
                <label>
                    <input type="text" class="form-control" name="search"
                           placeholder="<fmt:message key='catalogue.searchPlaceholder'/>"
                           value="<c:out value='${param.search}'/>">
                </label>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100"><fmt:message key="catalogue.searchBtn"/></button>
            </div>
        </form>

        <!-- book table -->
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th><fmt:message key="catalogue.col.isbn"/></th>
                        <th><fmt:message key="catalogue.col.title"/></th>
                        <th><fmt:message key="catalogue.col.author"/></th>
                        <th><fmt:message key="catalogue.col.genre"/></th>
                        <th><fmt:message key="catalogue.col.year"/></th>
                        <th><fmt:message key="catalogue.col.status"/></th>
                        <th><fmt:message key="catalogue.col.action"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td><c:out value="${book.isbn}"/></td>
                            <td><c:out value="${book.title}"/></td>
                            <td><c:out value="${book.author}"/></td>
                            <td><c:out value="${book.genre}"/></td>
                            <td><c:out value="${book.published_year}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${book.available}">
                                        <span class="badge bg-success"><fmt:message key="catalogue.available"/></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary"><fmt:message key="catalogue.onLoan"/></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${book.available}">
                                    <form action="loans" method="POST" class="d-inline">
                                        <input type="hidden" name="action" value="borrow">
                                        <input type="hidden" name="bookId" value="${book.book_Id}">
                                        <button type="submit" class="btn btn-sm btn-outline-primary"><fmt:message key="catalogue.borrowBtn"/></button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    </body>
    </html>
</fmt:bundle>