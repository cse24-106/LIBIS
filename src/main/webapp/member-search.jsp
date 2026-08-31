<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 8/31/2026
  Time: 8:33 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="bw.ac.bac.libis.model.Member" %>
<%@ page import="bw.ac.bac.libis.model.MemberDirectory" %>
<%@ page import="java.util.List" %>

<%
    String pageTitle = "Member Directory";

    String name = request.getParameter("name");
    String category = request.getParameter("category");
    String campus = request.getParameter("campus");

    boolean activeOnly = "true".equals(request.getParameter("activeOnly"));

    List<Member> members = MemberDirectory.search(
            name,
            category,
            campus,
            activeOnly
    );
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">

    <h1 class="mb-4">Member Directory</h1>

    <!-- Search Form -->
    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h5 class="card-title">Search Members</h5>

            <form method="get" action="${pageContext.request.contextPath}/member-search.jsp">

                <div class="row g-3">

                    <!-- Name -->
                    <div class="col-md-6">

                        <label for="name" class="form-label">
                            Name
                        </label>

                        <input type="text"
                               id="name"
                               name="name"
                               class="form-control"
                               placeholder="Search by name"
                               value="${param.name}">

                    </div>


                    <!-- Category -->
                    <div class="col-md-3">

                        <label for="category" class="form-label">
                            Category
                        </label>

                        <select id="category"
                                name="category"
                                class="form-select">

                            <option value="">All categories</option>

                            <option value="STUDENT"
                                ${param.category == 'STUDENT' ? 'selected' : ''}>
                                Student
                            </option>

                            <option value="STAFF"
                                ${param.category == 'STAFF' ? 'selected' : ''}>
                                Staff
                            </option>

                            <option value="ALUMNUS"
                                ${param.category == 'ALUMNUS' ? 'selected' : ''}>
                                Alumnus
                            </option>

                            <option value="PUBLIC"
                                ${param.category == 'PUBLIC' ? 'selected' : ''}>
                                Public
                            </option>

                        </select>

                    </div>


                    <!-- Campus -->
                    <div class="col-md-3">

                        <label for="campus" class="form-label">
                            Campus
                        </label>

                        <select id="campus"
                                name="campus"
                                class="form-select">

                            <option value="">All campuses</option>

                            <option value="GABORONE"
                                ${param.campus == 'GABORONE' ? 'selected' : ''}>
                                Gaborone
                            </option>

                            <option value="FRANCISTOWN"
                                ${param.campus == 'FRANCISTOWN' ? 'selected' : ''}>
                                Francistown
                            </option>

                        </select>

                    </div>

                </div>


                <!-- Active checkbox -->
                <div class="form-check mt-3">

                    <input type="checkbox"
                           id="activeOnly"
                           name="activeOnly"
                           value="true"
                           class="form-check-input"
                           ${param.activeOnly == 'true' ? 'checked' : ''}>

                    <label for="activeOnly"
                           class="form-check-label">

                        Show active members only

                    </label>

                </div>


                <!-- Buttons -->
                <div class="mt-4">

                    <button type="submit"
                            class="btn btn-primary">

                        Search

                    </button>

                    <a href="${pageContext.request.contextPath}/member-search.jsp"
                       class="btn btn-secondary">

                        Clear

                    </a>

                </div>

            </form>

        </div>

    </div>


    <!-- Results -->

    <h2 class="mb-3">Results</h2>

    <%
        if (members.isEmpty()) {
    %>

        <div class="alert alert-warning">
            No members matched your search.
        </div>

    <%
        } else {
    %>

        <p class="text-muted">
            Found <strong><%= members.size() %></strong> member(s).
        </p>

        <div class="table-responsive">

            <table class="table table-striped table-hover">

                <thead class="table-dark">

                    <tr>
                        <th>#</th>
                        <th>Membership Number</th>
                        <th>Full Name</th>
                        <th>Category</th>
                        <th>Campus</th>
                        <th>Age</th>
                        <th>Interests</th>
                    </tr>

                </thead>

                <tbody>

                <%
                    int number = 1;

                    for (Member member : members) {
                %>

                    <tr>

                        <td>
                            <%= number++ %>
                        </td>

                        <td>
                            <%= member.getMembershipNumber() %>
                        </td>

                        <td>
                            <%= member.getFullName() %>
                        </td>

                        <td>
                            <%= member.getCategory() %>
                        </td>

                        <td>
                            <%= member.getCampus() %>
                        </td>

                        <td>
                            <%= member.getAge() %>
                        </td>

                        <td>
                            <%= member.getInterestList() %>
                        </td>

                    </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>

    <%
        }
    %>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>