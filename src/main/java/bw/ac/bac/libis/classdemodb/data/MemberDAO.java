package bw.ac.bac.libis.classdemodb.data;

import bw.ac.bac.libis.classdemodb.model.Member;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public boolean validate(String email, String pass) {
        System.out.println("Validate called");

        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");

            conn = dataSource.getConnection();

            pst = conn.prepareStatement("select * from members where email=? and password=?");
            pst.setString(1, email);
            pst.setString(2, pass);

            rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println(e);
        }

        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public Member findByEmail(String email) {

        String sql = "SELECT member_Id, firstName, lastName, email, password\n" +
                     "FROM members\n" +
                     "WHERE email = ?\n";

        try {
            Context context = new InitialContext();

            DataSource dataSource = (DataSource)
                    context.lookup("java:comp/env/jdbc/libis");

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, email);

                try (ResultSet rs = pst.executeQuery()) {

                    if (rs.next()) {
                        return new Member(
                                rs.getInt("memberId"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to retrieve member.", e);
        }

        return null;
    }

    public boolean register(String firstName, String lastName, String email, String password) {
        String sql = "INSERT INTO members (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, 'member')";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, email);
                pst.setString(4, password);
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to register member.", e);
        }
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Member m = new Member(
                            rs.getInt("memberId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                    members.add(m);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all members.", e);
        }
        return members;
    }

    public Member findById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, memberId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return new Member(
                                rs.getInt("memberId"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to find member by ID.", e);
        }
        return null;
    }

    public List<Member> search(String keyword) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE firstName LIKE ? OR lastName LIKE ? OR email LIKE ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                String pattern = "%" + keyword + "%";
                pst.setString(1, pattern);
                pst.setString(2, pattern);
                pst.setString(3, pattern);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        members.add(new Member(
                                rs.getInt("memberId"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("email"),
                                rs.getString("password")
                        ));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to search members.", e);
        }
        return members;
    }

    public void update(Member member) {
        String sql = "UPDATE members SET firstName = ?, lastName = ?, email = ?, password = ? WHERE member_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, member.getFirstName());
                pst.setString(2, member.getLastName());
                pst.setString(3, member.getEmail());
                pst.setString(4, member.getPassword());
                pst.setInt(5, member.getMemberId());
                pst.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update member.", e);
        }
    }

    public void delete(int memberId) {
        String sql = "DELETE FROM members WHERE member_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, memberId);
                pst.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete member.", e);
        }
    }
}
