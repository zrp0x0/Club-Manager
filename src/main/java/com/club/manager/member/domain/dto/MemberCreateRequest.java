package com.club.manager.member.domain.dto;

import com.club.manager.member.domain.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name,

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String email
) {
    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name());
        member.setEmail(this.email());
        return member;
    }
}
