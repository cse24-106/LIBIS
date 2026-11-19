package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.config.DatabaseConfig;
import bw.ac.bac.libis.classdemodb.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO{

    private Member mapRowToMember(ResultSet rs) throws SQLException {
        return new Member(
                rs.getInt("member_id"),
                rs.getString("member_number"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("username"),
                rs.getString("role")
        );
    }

    @Override
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

    @Override
    public Member findById(int memberId) {
        String sql = "SELECT * FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<Member> search(String keyword) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE firstName LIKE ? OR lastName LIKE ? OR member_number LIKE ?";
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

    @Override
    public void create(Member member) {
        String sql = "INSERT INTO members (member_number, full_name, firstName, lastName, email, password, username, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, member.getMember_Number());
            pst.setString(2, member.getFull_name().trim());
            pst.setString(3, member.getFirstName());
            pst.setString(4, member.getLastName());
            pst.setString(5, member.getEmail());
            pst.setString(6, member.getPassword());
            pst.setString(7, member.getUsername());
            pst.setString(8, member.getRole());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating member", e);
        }
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE members SET member_number = ?, full_name = ?, firstname = ?, lastname = ?, email = ?, password = ?, username = ?, role = ? WHERE member_id = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, member.getMember_Number());
            pst.setString(2, member.getFull_name().trim());
            pst.setString(3, member.getFirstName());
            pst.setString(4, member.getLastName());
            pst.setString(5, member.getEmail());
            pst.setString(6, member.getPassword());
            pst.setString(7, member.getUsername());
            pst.setString(8, member.getRole());
            pst.setInt(9, member.getMember_Id());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating member", e);
        }
    }

    @Override
    public void delete(int memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";

            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, memberId);
                pst.executeUpdate();
            } catch (Exception e) {
            throw new RuntimeException("Failed to delete member.", e);
        }
    }
}
