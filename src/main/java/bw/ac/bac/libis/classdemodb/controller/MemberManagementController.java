package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.model.Member;
import bw.ac.bac.libis.classdemodb.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "memberManagement", value = "/librarian/members")
public class MemberManagementController extends HttpServlet {

    private MemberService memberService;

    @Override
    public void init() {
        memberService = new MemberService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listMembers(request, response);
                break;
            case "addForm":
                request.getRequestDispatcher("/WEB-INF/views/librarian/member-form.jsp").forward(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMember(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addMember(request, response);
        } else if ("update".equals(action)) {
            updateMember(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void listMembers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Member> members;
        if (keyword != null && !keyword.trim().isEmpty()) {
            members = memberService.searchMembers(keyword);
        } else {
            members = memberService.getAllMembers();
        }
        request.setAttribute("members", members);
        request.getRequestDispatcher("/WEB-INF/views/librarian/member-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Member member = memberService.getMemberById(id);
        request.setAttribute("member", member);
        request.getRequestDispatcher("/WEB-INF/views/librarian/member-form.jsp").forward(request, response);
    }

    private void addMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Member member = new Member(
                0,
                request.getParameter("memberNumber"),
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("username"),
                request.getParameter("role")
        );

        try {
            memberService.registerMember(member);
            response.sendRedirect("members?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("member", member);
            request.getRequestDispatcher("/WEB-INF/views/librarian/member-form.jsp").forward(request, response);
        }
    }

    private void updateMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("memberId"));

        Member member = new Member(
                id,
                request.getParameter("memberNumber"),
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("username"),
                request.getParameter("role")
        );

        try {
            memberService.updateMember(member);
            response.sendRedirect("members?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("member", member);
            request.getRequestDispatcher("/WEB-INF/views/librarian/member-form.jsp").forward(request, response);
        }
    }

    private void deleteMember(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        // this prevents self deletion
        String loggedInEmail = request.getRemoteUser();

        try {
            memberService.deleteMember(id, loggedInEmail);
            response.sendRedirect("members?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            listMembers(request, response);
        }
    }
}
