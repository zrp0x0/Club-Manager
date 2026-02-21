package com.club.manager.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.manager.member.domain.Member;
import com.club.manager.member.domain.MemberRepository;
import com.club.manager.member.domain.dto.MemberCreateRequest;
import com.club.manager.member.domain.dto.MemberResponse;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse registerMember(MemberCreateRequest request) {
        Member member = request.toEntity();
        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }

    @Transactional
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()  
            .map(MemberResponse::from)
            .toList();
    }

}
