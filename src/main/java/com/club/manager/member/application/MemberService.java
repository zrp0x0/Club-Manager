package com.club.manager.member.application;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.manager.common.exception.BusinessException;
import com.club.manager.common.exception.ErrorCode;
import com.club.manager.member.domain.Member;
import com.club.manager.member.domain.MemberRepository;
import com.club.manager.member.domain.dto.LoginRequest;
import com.club.manager.member.domain.dto.MemberCreateRequest;
import com.club.manager.member.domain.dto.MemberResponse;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberResponse registerMember(MemberCreateRequest request) {
        // 중복 이메일 가입 방지 (DB에 이미 unique가 걸려있지만, Early Return)
        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = request.toEntity();

        // DB에 넣기 넣기 직전에 평문 비밀번호를 꺼내서 암호화한 뒤 다시 세팅
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);

        Member savedMember = memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }

    @Transactional
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()  
                .map(MemberResponse::from)
                .toList();
    }
    
    @Transactional
    public MemberResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                            .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        return MemberResponse.from(member);
    }
}
