package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.config.DatabaseConfig;
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

    private DataSource getDataSource() {
        try {
            Context context = new InitialContext();
            return (DataSource) context.lookup("java:comp/env/jdbc/libis");
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup DataSource", e);
        }
    }

    private Member mapRowToMember(ResultSet rs) throws SQLException {
        return new Member(
                rs.getInt("member_id"),
                rs.getString("member_number"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("username"),
                rs.getString("role")
        );
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                members.add(mapRowToMember(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all members", e);
        }
        return members;
    }

    public Member findById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, memberId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMember(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member by ID", e);
        }
        return null;
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMember(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member by email", e);
        }
        return null;
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * FROM members WHERE username = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMember(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member by username", e);
        }
        return null;
    }

    public Member findByMemberNumber(String memberNumber) {
        String sql = "SELECT * FROM members WHERE member_number = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, memberNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMember(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member by member number", e);
        }
        return null;
    }

    public List<Member> search(String keyword) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE firstname LIKE ? OR lastname LIKE ? OR member_number LIKE ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            pst.setString(1, pattern);
            pst.setString(2, pattern);
            pst.setString(3, pattern);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    members.add(mapRowToMember(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching members", e);
        }
        return members;
    }

    public void create(Member member) {
        String sql = "INSERT INTO members (member_number, firstname, lastname, email, password, username, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, member.getMember_Number());
            pst.setString(2, member.getFirstName());
            pst.setString(3, member.getLastName());
            pst.setString(4, member.getEmail());
            pst.setString(5, member.getPassword());
            pst.setString(6, member.getUsername());
            pst.setString(7, member.getRole());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating member", e);
        }
    }

    public void update(Member member) {
        String sql = "UPDATE members SET member_number = ?, firstname = ?, lastname = ?, email = ?, password = ?, username = ?, role = ? WHERE member_id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, member.getMember_Number());
            pst.setString(2, member.getFirstName());
            pst.setString(3, member.getLastName());
            pst.setString(4, member.getEmail());
            pst.setString(5, member.getPassword());
            pst.setString(6, member.getUsername());
            pst.setString(7, member.getRole());
            pst.setInt(8, member.getMember_Id());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating member", e);
        }
    }

    public void delete(int memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";
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
