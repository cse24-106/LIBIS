<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<fmt:bundle basename="messages">

    <!DOCTYPE html>
    <html lang="${sessionScope['javax.servlet.jsp.jstl.fmt.locale'] != null ? sessionScope['javax.servlet.jsp.jstl.fmt.locale'] : 'en'}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="loans.heading"/></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

    <!-- nav bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="catalogue"><fmt:message key="app.title"/></a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="catalogue"><fmt:message key="nav.catalogue"/></a></li>
                    <li class="nav-item"><a class="nav-link active" href="loans?action=myLoans"><fmt:message key="nav.myLoans"/></a></li>
                </ul>
                <ul class="navbar-nav ms-auto align-items-center">
                    <li class="nav-item me-3">
                        <c:choose>
                            <c:when test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale'] == 'tn'}">
                                <a class="btn btn-sm btn-outline-light" href="loans?action=myLoans&lang=en">English</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-sm btn-outline-light" href="loans?action=myLoans&lang=tn">Setswana</a>
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
        <h2 class="mb-4"><fmt:message key="loans.heading"/></h2>

        <div class="card shadow-sm mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><fmt:message key="loans.currentHeading"/></h5>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${empty myLoans}">
                        <p class="p-3 text-muted mb-0"><fmt:message key="loans.noCurrent"/></p>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped mb-0">
                            <thead>
                            <tr>
                                <th><fmt:message key="catalogue.col.isbn"/></th>
                                <th><fmt:message key="catalogue.col.title"/></th>
                                <th><fmt:message key="catalogue.col.author"/></th>
                                <th><fmt:message key="catalogue.col.action"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="book" items="${myLoans}">
                                <tr>
                                    <td><c:out value="${book.isbn}"/></td>
                                    <td><c:out value="${book.title}"/></td>
                                    <td><c:out value="${book.author}"/></td>
                                    <td>
                                        <form action="loans" method="POST" class="d-inline">
                                            <input type="hidden" name="action" value="return">
                                            <input type="hidden" name="bookId" value="${book.book_Id}">
                                            <button type="submit" class="btn btn-sm btn-outline-success"><fmt:message key="loans.returnBtn"/></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    </body>
    </html>
</fmt:bundle>