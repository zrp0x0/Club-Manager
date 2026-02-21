package com.club.manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    
    @GetMapping("/api/hello")
    public String helloApi() {
        return "환영합니다! V00 동아리 관리 시스템 API 서버가 정상 작동 중입니다 🚀";
    }

}
