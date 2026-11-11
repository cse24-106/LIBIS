package bw.ac.bac.libis.classdemodb.data;

import bw.ac.bac.libis.classdemodb.model.Member;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
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
        } finally {
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

        String sql = "SELECT memberId, firstName, lastName, email, password\n" +
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
        return false;
    }
}
