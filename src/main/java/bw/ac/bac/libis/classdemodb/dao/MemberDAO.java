package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.model.Member;
import java.util.List;

public interface MemberDAO {
    List<Member> findAll();
    Member findById(int memberId);
    Member findByUsername(String username);
    Member findByMemberNumber(String memberNumber);
    Member findByEmail(String email);
    List<Member> search(String keyword);
    void create(Member member);
    void update(Member member);
    void delete(int memberId);
}
