package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.exception.ValidationException;
import bw.ac.bac.libis.classdemodb.model.Member;
import bw.ac.bac.libis.classdemodb.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "signupController", value = "/signup")
public class SignupController extends HttpServlet{

    private MemberService memberService;

    @Override
    public void init() {
        this.memberService = new MemberService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = trimOrNull(request.getParameter("firstName"));
        String lastName = trimOrNull(request.getParameter("lastName"));
        String username = trimOrNull(request.getParameter("username"));
        String email = trimOrNull(request.getParameter("email"));
        String password = request.getParameter("password");
        String terms = request.getParameter("terms");

        if (!"accepted".equals(terms)) {
            request.setAttribute("errorMessage", "You must accept the terms to create an account.");
            preserveFormValues(request, firstName, lastName, username, email);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
            return;
        }

        String fullName = buildFullName(firstName, lastName);
        String generatedMemberNumber = "MEM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Member newMember = new Member();
        newMember.setMember_Number(generatedMemberNumber);
        newMember.setFull_name(fullName);
        newMember.setFirstName(firstName);
        newMember.setLastName(lastName);
        newMember.setUsername(username);
        newMember.setEmail(email);
        newMember.setPassword(password);
        newMember.setRole("member");

        try {
            memberService.registerMember(newMember);
            request.getSession().setAttribute("signupSuccess", "Account created successfully. You can now log in.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } catch (ValidationException | IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            preserveFormValues(request, firstName, lastName, username, email);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        } catch (RuntimeException e) {
            log("Error registering new member during signup.", e);
            request.setAttribute("errorMessage", "Unable to create your account right now. Please try again later.");
            preserveFormValues(request, firstName, lastName, username, email);
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }
    }

    private String buildFullName(String firstName, String lastName) {
        String fn = (firstName == null) ? "" : firstName.trim();
        String ln = (lastName == null) ? "" : lastName.trim();
        return (fn + " " + ln).trim();
    }

    private String trimOrNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void preserveFormValues(HttpServletRequest request, String firstName,
                                    String lastName, String username, String email) {
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
    }
}
