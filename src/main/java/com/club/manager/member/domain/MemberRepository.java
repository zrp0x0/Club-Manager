package com.club.manager.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // email 필드값이 파라미터와 똑같은 데이터가 존재하는지 확인
    boolean existsByEmail(String email);

    // 이메일로 회원 정보를 통째로 찾아오는 메서드
    Optional<Member> findByEmail(String email);
}
