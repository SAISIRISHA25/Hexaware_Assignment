package com.jpa.movie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.movie.entity.Member;
import com.jpa.movie.repository.MemberRepository;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Integer id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member updateMember(Integer id, Member member) {
        Member existing = memberRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(member.getName());
            existing.setEmail(member.getEmail());
            existing.setMobileNo(member.getMobileNo());
            existing.setCity(member.getCity());
            existing.setPassword(member.getPassword());
            return memberRepository.save(existing);
        }
        return null;
    }

    public String deleteMember(Integer id) {
        Member existing = memberRepository.findById(id).orElse(null);
        if (existing != null) {
            memberRepository.deleteById(id);
            return "Member deleted successfully";
        }
        return "Member not found";
    }
}
