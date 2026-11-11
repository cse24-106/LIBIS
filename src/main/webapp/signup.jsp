<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Membership Signup</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/src/main/webapp/css/style.css">

</head>

<body class="auth-page">

<div class="card">

    <!-- Branding -->
    <div class="logo">
        LIBIS
    </div>

    <p class="subtitle">
        BAC Library Information System
    </p>

    <!-- Title -->
    <h2 class="title">
        Join the BAC Library
    </h2>

    <p class="subtitle">
        Create your membership account
    </p>


    <!-- Error Message -->
    <c:if test="${not empty errorMessage}">
        <p class="error-message">${errorMessage}</p>
    </c:if>


    <form action="${pageContext.request.contextPath}/hello-servlet"
          method="post">

        <!-- Hidden field -->
        <input type="hidden"
               name="source"
               value="signup">


        <div class="row g-3">

            <!-- First Name -->
            <div class="form-group">

                <label class="form-label"
                       for="fn">
                    First Name *
                </label>

                <div class="field">

                    <input
                            type="text"
                            id="fn"
                            name="firstName"
                            class="input-field"
                            placeholder="Enter your first name"
                            required>

                </div>

            </div>


            <!-- Last Name -->
            <div class="form-group">

                <label class="form-label"
                       for="ln">
                    Last Name *
                </label>

                <div class="field">

                    <input
                            type="text"
                            id="ln"
                            name="lastName"
                            class="input-field"
                            placeholder="Enter your last name"
                            required>

                </div>

            </div>


            <!-- Email -->
            <div class="form-group">

                <label class="form-label"
                       for="em">
                    Email *
                </label>

                <div class="field">

                    <input
                            type="email"
                            id="em"
                            name="email"
                            class="input-field"
                            placeholder="Enter your email"
                            required>

                </div>

            </div>


            <!-- Password -->
            <div class="form-group">

                <label class="form-label"
                       for="password">
                    Password *
                </label>

                <div class="field">

                    <input
                            type="password"
                            id="password"
                            name="password"
                            class="input-field"
                            placeholder="Create a password"
                            required>

                </div>

            </div>


            <!-- Terms -->
            <div class="terms">

                <input
                        type="checkbox"
                        id="terms"
                        name="terms"
                        value="accepted"
                        required>

                <label for="terms">
                    I accept the terms *
                </label>

            </div>

        </div>


        <!-- Submit -->
        <button
                type="submit"
                name="action"
                value="Review my details"
                class="btn">

            Create Account

        </button>


        <!-- Login link -->
        <a href="login.jsp"
           class="btn-link">

            Already have an account? Login

        </a>

    </form>

</div>

</body>
</html>