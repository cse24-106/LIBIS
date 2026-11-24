<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="statusCode" value="${requestScope['jakarta.servlet.error.status_code']}"/>
<c:set var="errorMessage" value="${requestScope['jakarta.servlet.error.message']}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LIBIS - Error <c:out value="${statusCode}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/libis-theme.css">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 560px;">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white d-flex align-items-center gap-2">
            <img src="${pageContext.request.contextPath}/images/libis-logo.png"
                 alt="LIBIS Logo"
                 class="libis-brand-logo">
            <h5 class="mb-0">LIBIS Library System</h5>
        </div>

        <div class="card-body p-4 text-center">
            <c:if test="${not empty statusCode}">
                <span class="badge bg-secondary mb-2 fs-6">Error <c:out value="${statusCode}"/></span>
            </c:if>

            <h3 class="mb-3">
                <c:choose>
                    <c:when test="${statusCode == 400}">Bad Request</c:when>
                    <c:when test="${statusCode == 403}">Access Denied</c:when>
                    <c:when test="${statusCode == 404}">Page Not Found</c:when>
                    <c:when test="${statusCode == 500}">Internal Server Error</c:when>
                    <c:otherwise>Something Went Wrong</c:otherwise>
                </c:choose>
            </h3>

            <p class="text-muted mb-4">
                <c:choose>
                    <c:when test="${statusCode == 400}">
                        <c:out value="${not empty errorMessage ? errorMessage : 'The request could not be understood due to missing or invalid parameters.'}"/>
                    </c:when>
                    <c:when test="${statusCode == 403}">
                        You do not have permission to access this page or perform this action.
                    </c:when>
                    <c:when test="${statusCode == 404}">
                        The page or resource you requested could not be found.
                    </c:when>
                    <c:when test="${statusCode == 500}">
                        An unexpected error occurred on the server. Please try again later.
                    </c:when>
                    <c:otherwise>
                        <c:out value="${not empty errorMessage ? errorMessage : 'An unexpected error occurred while processing your request.'}"/>
                    </c:otherwise>
                </c:choose>
            </p>

            <div class="d-flex justify-content-center gap-2">
                <a href="${pageContext.request.contextPath}/book" class="btn btn-primary">
                    Return to Catalogue
                </a>
                <a href="javascript:history.back()" class="btn btn-outline-secondary">
                    Go Back
                </a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>