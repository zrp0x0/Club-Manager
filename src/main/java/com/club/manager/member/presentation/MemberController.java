package com.club.manager.member.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.club.manager.common.response.ApiResponse;
import com.club.manager.member.application.MemberService;
import com.club.manager.member.domain.dto.MemberCreateRequest;
import com.club.manager.member.domain.dto.MemberResponse;

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
}
