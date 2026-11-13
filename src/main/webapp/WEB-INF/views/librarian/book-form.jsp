<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${empty book ? 'Add Book' : 'Edit Book'}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 600px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="card-title mb-4">
                <c:choose>
                    <c:when test="${empty book}">Add New Book to Catalogue</c:when>
                    <c:otherwise>Edit Book Details</c:otherwise>
                </c:choose>
            </h3>

            <!-- error feedback -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>

            <form action="books" method="POST">
                <c:choose>
                    <c:when test="${empty book}">
                        <input type="hidden" name="action" value="add">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="bookId" value="<c:out value='${book.book_Id}'/>">
                    </c:otherwise>
                </c:choose>

                <div class="mb-3">
                    <label for="isbn" class="form-label">ISBN</label>
                    <input type="text" class="form-control" id="isbn" name="isbn" value="<c:out value='${book.isbn}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="title" class="form-label">Book Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="<c:out value='${book.title}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="author" class="form-label">Author</label>
                    <input type="text" class="form-control" id="author" name="author" value="<c:out value='${book.author}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="genre" class="form-label">Genre</label>
                    <input type="text" class="form-control" id="genre" name="genre" value="<c:out value='${book.genre}'/>">
                </div>

                <div class="mb-3">
                    <label for="publishedYear" class="form-label">Published Year</label>
                    <input type="number" class="form-control" id="publishedYear" name="publishedYear" value="<c:out value='${book.published_year}'/>" required>
                </div>

                <div class="mb-3">
                    <label for="synopsis" class="form-label">Synopsis</label>
                    <textarea class="form-control" id="synopsis" name="synopsis" rows="3"><c:out value="${book.synopsis}"/></textarea>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="books?action=list" class="btn btn-outline-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary"><c:out value="${empty book ? 'Add Book' : 'Save Changes'}"/></button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>