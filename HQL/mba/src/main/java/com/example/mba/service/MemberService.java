package com.example.mba.service;


import java.util.List;

import com.example.mba.dao.MemberDAO;
import com.example.mba.doaimpl.MemberDAOImpl;
import com.example.mba.entity.Member;

public class MemberService {

    MemberDAO memberDAO = new MemberDAOImpl();

    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    public List<Member> showAllMembers() {
        return memberDAO.showAllMembers();
    }

    public Member searchMemberById(int memberId) {
        return memberDAO.searchMemberById(memberId);
    }

    public int deleteMember(int memberId) {
        return memberDAO.deleteMember(memberId);
    }
}
