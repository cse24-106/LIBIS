<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.setCharacterEncoding("UTF-8");

    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String email = request.getParameter("email");

    String phone = request.getParameter("phone");
    String dateOfBirth = request.getParameter("dateOfBirth");
    String category = request.getParameter("category");
    String campus = request.getParameter("campus");

    String[] interests = request.getParameterValues("interests");

    String newsletter = request.getParameter("newsletter");
    String terms = request.getParameter("terms");
    String source = request.getParameter("source");

    String pageTitle = "Membership Complete";
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="text-center">

    <h1>Thank You, <%= firstName %>!</h1>

    <p class="lead">
        Your BAC Library membership details have been confirmed.
    </p>

    <div class="alert alert-info">

        <strong>Important:</strong>

        Nothing has been stored yet.

        This exercise only demonstrates collecting,
        validating and carrying form data between JSP pages.

    </div>

    <p>
        Applicant:
        <strong><%= firstName %> <%= lastName %></strong>
    </p>

    <p>
        Email:
        <strong><%= email %></strong>
    </p>

    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
        Return to Home
    </a>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>