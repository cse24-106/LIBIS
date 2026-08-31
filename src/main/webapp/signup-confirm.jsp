<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 8/31/2026
  Time: 5:46 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    // Make sure POST data is interpreted as UTF-8
    request.setCharacterEncoding("UTF-8");

    // Read all submitted parameters
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
    String action = request.getParameter("action");

    // List of validation problems
    java.util.List<String> errors = new java.util.ArrayList<>();

    // Validate first name
    if (firstName == null || firstName.trim().isEmpty()) {
        errors.add("First name is required.");
    }

    // Validate last name
    if (lastName == null || lastName.trim().isEmpty()) {
        errors.add("Last name is required.");
    }

    // Validate email
    if (email == null || email.trim().isEmpty()) {
        errors.add("Email is required.");
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        errors.add("Please provide a valid email address.");
    }

    // Validate terms checkbox
    if (!"accepted".equals(terms)) {
        errors.add("You must accept the terms and conditions.");
    }

    String pageTitle = errors.isEmpty()
            ? "Confirm Membership"
            : "Signup Errors";
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%
    if (!errors.isEmpty()) {
%>

    <!-- ========================= -->
    <!-- VALIDATION ERRORS -->
    <!-- ========================= -->

    <div class="alert alert-danger">
        <h2 class="alert-heading">Please correct the following:</h2>

        <ul>
            <%
                for (String error : errors) {
            %>
                <li><%= error %></li>
            <%
                }
            %>
        </ul>

        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-danger">
            Back to signup form
        </a>
    </div>

<%
    } else {
%>

    <!-- ========================= -->
    <!-- CONFIRMATION PAGE -->
    <!-- ========================= -->

    <h1>Confirm Your Membership Details</h1>

    <p class="lead">
        Please check the information below before confirming.
    </p>

    <div class="table-responsive">

        <table class="table table-bordered table-striped">

            <tr>
                <th>First Name</th>
                <td><%= firstName %></td>
            </tr>

            <tr>
                <th>Last Name</th>
                <td><%= lastName %></td>
            </tr>

            <tr>
                <th>Email</th>
                <td><%= email %></td>
            </tr>

            <tr>
                <th>Mobile</th>
                <td>
                    <%= (phone == null || phone.trim().isEmpty())
                            ? "(not given)"
                            : phone %>
                </td>
            </tr>

            <tr>
                <th>Date of Birth</th>
                <td><%= dateOfBirth %></td>
            </tr>

            <tr>
                <th>Membership Category</th>
                <td><%= category %></td>
            </tr>

            <tr>
                <th>Campus</th>
                <td><%= campus %></td>
            </tr>

            <tr>
                <th>Interests</th>
                <td>
                    <%
                        if (interests == null || interests.length == 0) {
                    %>
                        (none selected)
                    <%
                        } else {
                            for (String interest : interests) {
                    %>
                        <%= interest %><br>
                    <%
                            }
                        }
                    %>
                </td>
            </tr>

            <tr>
                <th>Newsletter</th>
                <td>
                    <%= newsletter != null ? "Yes" : "No" %>
                </td>
            </tr>

            <tr>
                <th>Terms Accepted</th>
                <td>Yes</td>
            </tr>

            <tr>
                <th>Source</th>
                <td><%= source %></td>
            </tr>

        </table>

    </div>

    <!-- ========================= -->
    <!-- CONFIRM FORM -->
    <!-- ========================= -->

    <form action="${pageContext.request.contextPath}/signup-complete.jsp" method="post">

        <!-- Carry the values forward -->

        <input type="hidden" name="firstName"
               value="<%= firstName %>">

        <input type="hidden" name="lastName"
               value="<%= lastName %>">

        <input type="hidden" name="email"
               value="<%= email %>">

        <input type="hidden" name="phone"
               value="<%= phone == null ? "" : phone %>">

        <input type="hidden" name="dateOfBirth"
               value="<%= dateOfBirth %>">

        <input type="hidden" name="category"
               value="<%= category %>">

        <input type="hidden" name="campus"
               value="<%= campus %>">

        <%
            if (interests != null) {
                for (String interest : interests) {
        %>

            <input type="hidden"
                   name="interests"
                   value="<%= interest %>">

        <%
                }
            }
        %>

        <%
            if (newsletter != null) {
        %>

            <input type="hidden"
                   name="newsletter"
                   value="yes">

        <%
            }
        %>

        <input type="hidden"
               name="terms"
               value="accepted">

        <input type="hidden"
               name="source"
               value="<%= source %>">

        <button type="submit" class="btn btn-success">
            Confirm Membership
        </button>

        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-secondary">
            Edit Details
        </a>

    </form>

<%
    }
%>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>