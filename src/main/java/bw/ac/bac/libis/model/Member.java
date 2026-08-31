package bw.ac.bac.libis.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Member {

    // =========================
    // Fields
    // =========================

    private String membershipNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String category;
    private String campus;
    private List<String> interests;
    private boolean newsletter;
    private LocalDate joinDate;
    private boolean active;


    // =========================
    // No-argument constructor
    // =========================

    public Member() {
        this.interests = new ArrayList<>();
    }


    // =========================
    // All-arguments constructor
    // =========================

    public Member(String membershipNumber,
                  String firstName,
                  String lastName,
                  String email,
                  String phone,
                  LocalDate dateOfBirth,
                  String category,
                  String campus,
                  List<String> interests,
                  boolean newsletter,
                  LocalDate joinDate,
                  boolean active) {

        this.membershipNumber = membershipNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
        this.campus = campus;

        if (interests == null) {
            this.interests = new ArrayList<>();
        } else {
            this.interests = new ArrayList<>(interests);
        }

        this.newsletter = newsletter;
        this.joinDate = joinDate;
        this.active = active;
    }


    // =========================
    // Getters
    // =========================

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCategory() {
        return category;
    }

    public String getCampus() {
        return campus;
    }

    public List<String> getInterests() {
        return interests;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public boolean isActive() {
        return active;
    }


    // =========================
    // Setters
    // =========================

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setInterests(List<String> interests) {
        if (interests == null) {
            this.interests = new ArrayList<>();
        } else {
            this.interests = new ArrayList<>(interests);
        }
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    // =========================
    // Derived properties
    // =========================

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }

        return Period.between(
                dateOfBirth,
                LocalDate.now()
        ).getYears();
    }

    public String getInterestList() {
        if (interests == null || interests.isEmpty()) {
            return "-";
        }

        return String.join(", ", interests);
    }
}
