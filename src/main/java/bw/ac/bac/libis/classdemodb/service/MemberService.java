package bw.ac.bac.libis.classdemodb.service;

import bw.ac.bac.libis.classdemodb.dao.MemberDAO;
import bw.ac.bac.libis.classdemodb.model.Member;

import java.util.List;
import java.util.UUID;

public class MemberService {

    private final MemberDAO memberDao;

    public MemberService() {
        this.memberDao = new MemberDAO();
    }

    public List<Member> getAllMembers() {
        return memberDao.findAll();
    }

    public Member getMemberById(int id) {
        return memberDao.findById(id);
    }

    public List<Member> searchMembers(String keyword) {
        return memberDao.search(keyword);
    }

    public void registerMember(Member member) {
        validateMemberInput(member, true);

        //auto gen member number if blank
        if (member.getMember_Number() == null || member.getMember_Number().trim().isEmpty()) {
            member.setMember_Number("MEM-0" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }

        // checking duplicates
        if (memberDao.findByMemberNumber(member.getMember_Number()) != null) {
            throw new IllegalArgumentException("Member number already exists.");
        }
        if (memberDao.findByUsername(member.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (memberDao.findByEmail(member.getEmail()) != null) {
            throw new IllegalArgumentException("Email address is already registered.");
        }
        if (member.getRole() == null || member.getRole().trim().isEmpty()) {
            member.setRole("member");
        }

        memberDao.create(member);
    }

    public void updateMember(Member updatedMember) {
        validateMemberInput(updatedMember, false);

        Member existing = memberDao.findById(updatedMember.getMember_Id());
        if (existing == null) {
            throw new IllegalArgumentException("Member not found.");
        }

        // checks if editing password is blank
        if (updatedMember.getPassword() == null || updatedMember.getPassword().trim().isEmpty()) {
            updatedMember.setPassword(existing.getPassword());
        }

        // checks uniqueness excluding current record
        Member byNumber = memberDao.findByMemberNumber(updatedMember.getMember_Number());
        if (byNumber != null && byNumber.getMember_Id() != updatedMember.getMember_Id()) {
            throw new IllegalArgumentException("Member number is already assigned to another member.");
        }

        Member byUsername = memberDao.findByUsername(updatedMember.getUsername());
        if (byUsername != null && byUsername.getMember_Id() != updatedMember.getMember_Id()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        Member byEmail = memberDao.findByEmail(updatedMember.getEmail());
        if (byEmail != null && byEmail.getMember_Id() != updatedMember.getMember_Id()) {
            throw new IllegalArgumentException("Email address is already used by another member.");
        }

        memberDao.update(updatedMember);
    }

    public void deleteMember(int memberId, String loggedInUsername) {
        Member memberToDelete = memberDao.findById(memberId);
        if (memberToDelete == null) {
            throw new IllegalArgumentException("Member not found.");
        }

        // Prevent librarian from deleting their own acc
        if (memberToDelete.getUsername().equals(loggedInUsername)) {
            throw new IllegalStateException("Security restriction: You cannot delete your own librarian account.");
        }

        memberDao.delete(memberId);
    }

    private void validateMemberInput(Member m, boolean isNew) {
        if (m.getMember_Number() == null || m.getMember_Number().trim().isEmpty()) {
            throw new IllegalArgumentException("Member number is required.");
        }
        if (m.getFull_name() == null || m.getFull_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required.");
        }
        if (m.getEmail() == null || !m.getEmail().contains("@") || !m.getEmail().contains(".")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (m.getUsername() == null || m.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (isNew && (m.getPassword() == null || m.getPassword().trim().isEmpty())) {
            throw new IllegalArgumentException("Password is required for new members.");
        }
    }
}
