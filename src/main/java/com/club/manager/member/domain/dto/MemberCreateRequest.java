package com.club.manager.member.domain.dto;

import com.club.manager.member.domain.Member;
import com.club.manager.member.domain.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name,

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    String password
) {
    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name());
        member.setEmail(this.email());
        member.setPassword(this.password());

        // 새로 가입하는 유저는 기본적으로 일반 사용자 권한을 부여
        member.setRole(Role.USER);
        return member;
    }
}
