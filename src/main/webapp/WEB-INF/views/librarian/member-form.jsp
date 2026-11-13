<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${empty member ? 'Add Member' : 'Edit Member'}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 600px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="card-title mb-4">
                <c:choose>
                    <c:when test="${empty member}">Register New Member</c:when>
                    <c:otherwise>Edit Member Details</c:otherwise>
                </c:choose>
            </h3>

            <!-- Error Feedback -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>

            <form action="members" method="POST">
                <c:choose>
                    <c:when test="${empty member}">
                        <input type="hidden" name="action" value="add">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="memberId" value="<c:out value='${member.memberId}'/>">
                    </c:otherwise>
                </c:choose>

                <div class="mb-3">
                    <label for="memberNumber" class="form-label">Member Number (e.g. LIB-001)</label>
                    <input type="text" class="form-control" id="memberNumber" name="memberNumber" value="<c:out value='${member.memberNumber}'/>" required>
                </div>

                <div class="row mb-3">
                    <div class="col">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value='${member.firstName}'/>" required>
                    </div>
                    <div class="col">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value='${member.lastName}'/>" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username" value="<c:out value='${member.username}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email Address</label>
                    <input type="email" class="form-control" id="email" name="email" value="<c:out value='${member.email}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password <c:if test="${not empty member}"><small class="text-muted">(Leave blank to keep existing)</small></c:if></label>
                    <input type="password" class="form-control" id="password" name="password" ${empty member ? 'required' : ''}>
                </div>

                <div class="mb-3">
                    <label for="role" class="form-label">Role</label>
                    <select class="form-select" id="role" name="role" required>
                        <option value="member" ${member.role == 'member' ? 'selected' : ''}>Member</option>
                        <option value="librarian" ${member.role == 'librarian' ? 'selected' : ''}>Librarian</option>
                    </select>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="members?action=list" class="btn btn-outline-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary"><c:out value="${empty member ? 'Create Member' : 'Save Changes'}"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>