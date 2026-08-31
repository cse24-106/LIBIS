<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 8/31/2026
  Time: 5:40 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String pageTitle = "Membership Signup";
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Join the BAC Library</h1>

<p>All fields marked * are required. Nothing is saved until you confirm.</p>

<form action="${pageContext.request.contextPath}/signup-confirm.jsp" method="post">

    <!-- First Name -->
    <label for="fn">First name *</label>
    <input type="text"
           id="fn"
           name="firstName"
           required>

    <!-- Last Name -->
    <label for="ln">Last name *</label>
    <input type="text"
           id="ln"
           name="lastName"
           required>

    <!-- Email -->
    <label for="em">Email *</label>
    <input type="email"
           id="em"
           name="email"
           required>

    <!-- Phone -->
    <label for="ph">Mobile</label>
    <input type="tel"
           id="ph"
           name="phone"
           placeholder="+267 71 234 567">

    <!-- Date of Birth -->
    <label for="dob">Date of birth *</label>
    <input type="date"
           id="dob"
           name="dateOfBirth"
           required>

    <!-- Membership Category -->
    <fieldset>
        <legend>Membership category *</legend>

        <label>
            <input type="radio"
                   name="category"
                   value="STUDENT"
                   checked>
            Student
        </label>

        <label>
            <input type="radio"
                   name="category"
                   value="STAFF">
            Staff
        </label>

        <label>
            <input type="radio"
                   name="category"
                   value="ALUMNUS">
            Alumnus
        </label>

        <label>
            <input type="radio"
                   name="category"
                   value="PUBLIC">
            Public
        </label>
    </fieldset>

    <!-- Campus -->
    <label for="cp">Campus *</label>
    <select id="cp" name="campus" required>
        <option value="" disabled selected>Choose a campus</option>
        <option value="GABORONE">Gaborone</option>
        <option value="FRANCISTOWN">Francistown</option>
    </select>

    <!-- Interests -->
    <label for="in">Interests (hold Ctrl to pick several)</label>
    <select id="in"
            name="interests"
            multiple
            size="4">

        <option value="programming">Programming</option>
        <option value="databases">Databases</option>
        <option value="networks">Networks</option>
        <option value="business">Business</option>

    </select>

    <!-- Newsletter -->
    <label>
        <input type="checkbox"
               name="newsletter"
               value="yes">
        Send me the newsletter
    </label>

    <!-- Terms -->
    <label>
        <input type="checkbox"
               name="terms"
               value="accepted"
               required>
        I accept the terms *
    </label>

    <!-- Hidden field -->
    <input type="hidden"
           name="source"
           value="web-signup">

    <!-- Submit -->
    <input type="submit"
           name="action"
           value="Review my details">

</form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
