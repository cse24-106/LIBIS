<%
    String pageTitle = "Home";
%>

<%@ include file="/WEB-INF/jspf/header.jspf" %>


<!-- Hero Section -->

<section class="libis-hero">

    <div class="row align-items-center">

        <div class="col-lg-8">

            <span class="badge rounded-pill mb-3"
                  style="background-color: #AD8B73;">

                BAC Library

            </span>

            <h1>
                Welcome to LIBIS
            </h1>

            <p class="lead mt-3">
                The Library Information System for
                Botswana Accountancy College.
            </p>

            <p>
                Manage library membership, explore members,
                and keep your library information organised.
            </p>

            <div class="mt-4">

                <a href="${pageContext.request.contextPath}/signup.jsp"
                   class="btn btn-libis me-2">

                    Join the Library

                </a>

                <a href="${pageContext.request.contextPath}/member-search.jsp"
                   class="btn btn-libis-outline">

                    Browse Members

                </a>

            </div>

        </div>


        <div class="col-lg-4 text-center mt-4 mt-lg-0">

            <div class="libis-icon mx-auto"
                 style="width: 100px;
                        height: 100px;
                        font-size: 3rem;">

                📚

            </div>

            <h4 class="mt-3">
                Learn. Read. Grow.
            </h4>

        </div>

    </div>

</section>


<!-- Features -->

<div class="row g-4">


    <!-- Membership -->

    <div class="col-md-6">

        <div class="card libis-card h-100">

            <div class="card-body p-4">

                <div class="libis-icon">
                    👤
                </div>

                <h3 class="card-title">
                    Library Membership
                </h3>

                <p class="card-text">
                    Join the BAC library by providing your
                    personal details, membership category,
                    campus and interests.
                </p>

                <a href="${pageContext.request.contextPath}/signup.jsp"
                   class="btn btn-libis">

                    Become a Member

                </a>

            </div>

        </div>

    </div>


    <!-- Directory -->

    <div class="col-md-6">

        <div class="card libis-card h-100">

            <div class="card-body p-4">

                <div class="libis-icon">
                    🔎
                </div>

                <h3 class="card-title">
                    Member Directory
                </h3>

                <p class="card-text">
                    Search and filter existing library members
                    by name, category, campus and membership
                    status.
                </p>

                <a href="${pageContext.request.contextPath}/member-search.jsp"
                   class="btn btn-libis-outline">

                    Find a Member

                </a>

            </div>

        </div>

    </div>

</div>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>