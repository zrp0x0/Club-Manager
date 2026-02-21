package com.club.manager.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // email 필드값이 파라미터와 똑같은 데이터가 존재하는지 확인
    boolean existsByEmail(String email);
}
