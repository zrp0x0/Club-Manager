package com.club.manager.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.manager.common.exception.BusinessException;
import com.club.manager.common.exception.ErrorCode;
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
        // 중복 이메일 가입 방지 (DB에 이미 unique가 걸려있지만, Early Return)
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

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
