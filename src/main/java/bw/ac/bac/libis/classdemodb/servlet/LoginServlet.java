package bw.ac.bac.libis.classdemodb.servlet;

import bw.ac.bac.libis.classdemodb.data.MemberDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    MemberDAO userDAO = new MemberDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            handleLogout(request, response);
        } else {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        if (action == null) {
            action = "signup";
        }

        if ("signup".equals(action)) {
            handleSignup(request, response);
        } else {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Unknown action"
            );
        }
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (isBlank(firstName) || isBlank(lastName)
                || isBlank(email) || isBlank(password)) {

            request.setAttribute(
                    "errorMessage",
                    "Please complete all required fields."
            );

            request.getRequestDispatcher("/signup.jsp")
                    .forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute(
                    "errorMessage",
                    "Your passwords do not match."
            );

            request.getRequestDispatcher("/signup.jsp")
                    .forward(request, response);
            return;
        }

        if (userDAO.findByEmail(email) != null) {
            request.setAttribute(
                    "errorMessage",
                    "An account with this email already exists."
            );

            request.getRequestDispatcher("/signup.jsp")
                    .forward(request, response);
            return;
        }

        boolean registered = userDAO.register(
                firstName, lastName, email, password
        );

        if (registered) {
            request.setAttribute(
                    "successMessage",
                    "Account created successfully. You can now log in."
            );

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute(
                    "errorMessage",
                    "We could not create your account. Please try again."
            );

            request.getRequestDispatcher("/signup.jsp")
                    .forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.logout();

        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

}