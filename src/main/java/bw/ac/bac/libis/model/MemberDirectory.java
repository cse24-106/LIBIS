package bw.ac.bac.libis.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberDirectory {

    private static final List<Member> MEMBERS = new ArrayList<>();

    // Static initializer
    static {
        MEMBERS.add(new Member(
                "BAC-2026-0001",
                "Felix",
                "Kagwi",
                "felix@example.com",
                "+267 71 234 567",
                LocalDate.of(2006, 5, 15),
                "STUDENT",
                "GABORONE",
                List.of("programming", "networks"),
                true,
                LocalDate.of(2026, 1, 15),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0002",
                "Naledi",
                "Molefe",
                "naledi@example.com",
                "+267 72 345 678",
                LocalDate.of(2004, 8, 20),
                "STUDENT",
                "FRANCISTOWN",
                List.of("databases", "programming"),
                true,
                LocalDate.of(2026, 1, 20),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0003",
                "Thabo",
                "Motsamai",
                "thabo@example.com",
                null,
                LocalDate.of(2003, 3, 10),
                "STUDENT",
                "FRANCISTOWN",
                List.of(),
                false,
                LocalDate.of(2026, 2, 5),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0004",
                "Boitumelo",
                "Kebadire",
                "boitumelo@example.com",
                "+267 73 456 789",
                LocalDate.of(2002, 11, 25),
                "STUDENT",
                "GABORONE",
                List.of("business", "databases"),
                true,
                LocalDate.of(2026, 2, 12),
                false
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0005",
                "Kabelo",
                "Dube",
                "kabelo@example.com",
                "+267 74 567 890",
                LocalDate.of(1998, 6, 18),
                "STAFF",
                "GABORONE",
                List.of("networks", "programming"),
                true,
                LocalDate.of(2026, 1, 8),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0006",
                "Mpho",
                "Sechele",
                "mpho@example.com",
                "+267 75 678 901",
                LocalDate.of(1995, 9, 3),
                "STAFF",
                "FRANCISTOWN",
                List.of("business"),
                false,
                LocalDate.of(2026, 1, 25),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0007",
                "Amantle",
                "Mooketsi",
                "amantle@example.com",
                "+267 76 789 012",
                LocalDate.of(1997, 1, 14),
                "ALUMNUS",
                "GABORONE",
                List.of("databases", "business"),
                true,
                LocalDate.of(2026, 3, 1),
                false
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0008",
                "Lesego",
                "Nkawana",
                "lesego@example.com",
                "+267 77 890 123",
                LocalDate.of(1990, 4, 30),
                "PUBLIC",
                "FRANCISTOWN",
                List.of("programming"),
                false,
                LocalDate.of(2026, 2, 18),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0009",
                "Odirile",
                "Molebatsi",
                "odirile@example.com",
                "+267 78 901 234",
                LocalDate.of(2005, 12, 7),
                "STUDENT",
                "GABORONE",
                List.of("networks", "databases"),
                true,
                LocalDate.of(2026, 3, 10),
                true
        ));

        MEMBERS.add(new Member(
                "BAC-2026-0010",
                "Keitumetse",
                "Radebe",
                "keitumetse@example.com",
                "+267 79 012 345",
                LocalDate.of(2001, 7, 22),
                "STUDENT",
                "FRANCISTOWN",
                List.of("business", "networks"),
                true,
                LocalDate.of(2026, 3, 15),
                false
        ));
    }


    // Return all members without allowing modification
    public static List<Member> findAll() {
        return Collections.unmodifiableList(MEMBERS);
    }


    // Search members
    public static List<Member> search(
            String name,
            String category,
            String campus,
            boolean activeOnly) {

        List<Member> results = new ArrayList<>();

        for (Member member : MEMBERS) {

            // Name filter
            if (name != null && !name.isBlank()) {
                String searchName = name.trim().toLowerCase();

                if (!member.getFullName().toLowerCase().contains(searchName)) {
                    continue;
                }
            }

            // Category filter
            if (category != null && !category.isBlank()) {
                if (!member.getCategory().equals(category)) {
                    continue;
                }
            }

            // Campus filter
            if (campus != null && !campus.isBlank()) {
                if (!member.getCampus().equals(campus)) {
                    continue;
                }
            }

            // Active filter
            if (activeOnly && !member.isActive()) {
                continue;
            }

            results.add(member);
        }

        return results;
    }
}