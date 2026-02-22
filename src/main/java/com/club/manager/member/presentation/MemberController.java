package com.club.manager.member.presentation;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.club.manager.common.response.ApiResponse;
import com.club.manager.member.application.MemberService;
import com.club.manager.member.domain.dto.LoginRequest;
import com.club.manager.member.domain.dto.MemberCreateRequest;
import com.club.manager.member.domain.dto.MemberResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> addMember(
        @Valid @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.registerMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<MemberResponse> responses = memberService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse>> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletRequest httpServletRequest // 웹 요청의 모든 정보가 담긴 객체
    ) {
        // 서비스 계층에 검증을 맡김
        MemberResponse response = memberService.login(request);

        // 검증을 통과했다면, 세션을 발급
        // getSession(true): 기존에 쓰던 세션이 있으면 그걸 주고, 없으면 새로 만들어서 줌
        HttpSession session = httpServletRequest.getSession(true);

        // 발급받은 세션(서버의 메모리 공간)에 로그인 회원의 ID(식별자)를 적어둠
        // "LOGIN_MEMBER"라는 이름표를 붙여서 보관함
        session.setAttribute("LOGIN_MEMBER", response.id());
        
        // 성공 응답을 보냄
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
        HttpServletRequest request
    ) {
        // 현재 요청에 포함된 세션을 가져옴
        // getSession(false): 기존 세션이 있으면 가져오고, 없으면 새로 만들지 말고 null
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 서버 메모리에서 세션 장부 파기
        }

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
