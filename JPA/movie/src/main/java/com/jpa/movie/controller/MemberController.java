package com.jpa.movie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jpa.movie.entity.Member;
import com.jpa.movie.service.MemberService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public Member addMember(@Valid @RequestBody Member member) {
        return memberService.addMember(member);
    }

    @GetMapping("/all")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Integer id) {
        return memberService.getMemberById(id);
    }

    @PutMapping("/update/{id}")
    public Member updateMember(@PathVariable Integer id, @Valid @RequestBody Member member) {
        return memberService.updateMember(id, member);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable Integer id) {
        return memberService.deleteMember(id);
    }
}