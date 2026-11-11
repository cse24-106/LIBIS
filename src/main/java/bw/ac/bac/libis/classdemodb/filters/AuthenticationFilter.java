package bw.ac.bac.libis.classdemodb.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/welcome.jsp",
        "/welcome",
        "/books/*",
        "/catalogue",
        "/library",
        "/book",
        "/book-details.jsp",
        "/bookact"
})

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("loggedInMember") != null;

        if (loggedIn) {
            chain.doFilter(request, response);
            System.out.println("AuthenticationFilter logged in");
        } else {
            System.out.println("AuthenticationFilter not logged in");
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
