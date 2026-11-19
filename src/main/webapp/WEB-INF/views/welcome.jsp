<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- handles language switching via query parameter -->
<c:if test="${not empty param.lang}">
    <c:set target="${sessionScope}" property="currentLang" value="${param.lang}"/>
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

    <!-- header -->
    <jsp:include page="/WEB-INF/views/fragments/header.jspf"/>

    <div class="container">
        <h2 class="mb-4"><fmt:message key="catalogue.heading"/></h2>

        <!-- Search Form -->
        <form action="${pageContext.request.contextPath}/book" method="GET" class="row g-3 mb-4">
            <input type="hidden" name="action" value="searchBook">
            <div class="col-md-6">
                <input type="text" class="form-control" name="searchTerm"
                       placeholder="<fmt:message key='catalogue.searchPlaceholder'/>"
                       value="<c:out value='${param.searchTerm}'/>">
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

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/fragments/footer.jspf"/>

    </body>
    </html>
</fmt:bundle>