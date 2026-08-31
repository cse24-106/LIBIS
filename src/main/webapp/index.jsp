<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String pageTitle = "BAC Library";
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<head>
  <title>JSP - Hello World</title>
</head>

<div class="text-center">

    <h1>Welcome to BAC</h1>

    <p class="lead">
        Welcome to the BAC Library Information System.
    </p>

    <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-primary">
        Join the BAC Library
    </a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>