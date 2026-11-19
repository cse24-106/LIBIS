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
        <title><fmt:message key="loans.heading"/></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/libis-theme.css">
    </head>
    <body class="bg-light">

    <!-- header -->
    <jsp:include page="/WEB-INF/views/fragments/header.jspf"/>

    <div class="container">
        <h2 class="mb-4"><fmt:message key="loans.heading"/></h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:out value="${errorMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

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

    <!-- footer -->
    <jsp:include page="/WEB-INF/views/fragments/footer.jspf"/>

    </body>
    </html>
</fmt:bundle>