package com.example.mba.dao;


import java.util.List;
import com.example.mba.entity.Member;

public interface MemberDAO {
    void addMember(Member member);
    List<Member> showAllMembers();
    Member searchMemberById(int memberId);
    int deleteMember(int memberId);
}
