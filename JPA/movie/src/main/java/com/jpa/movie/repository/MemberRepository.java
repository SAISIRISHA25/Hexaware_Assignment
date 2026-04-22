package com.jpa.movie.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.jpa.movie.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
