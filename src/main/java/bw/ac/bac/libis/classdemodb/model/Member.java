package bw.ac.bac.libis.classdemodb.model;

public class Member {
    private int member_Id;
    private String member_number;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String role;

    public Member(){}

    public Member(int member_Id, String member_number, String firstName, String lastName, String email, String password, String username, String role) {
        this.member_Id = member_Id;
        this.member_number = member_number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public int getMember_Id() {
        return member_Id;
    }

    public void setMember_Id(int member_Id) {
        this.member_Id = member_Id;
    }

    public String getMember_Number() {
        return member_number;
    }

    public void setMember_Number(String member_Number) {
        this.member_number = member_Number;
    }

    public String getFull_name() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

