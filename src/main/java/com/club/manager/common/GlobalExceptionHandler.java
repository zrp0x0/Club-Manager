package com.club.manager.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.club.manager.common.exception.BusinessException;
import com.club.manager.common.exception.ErrorCode;
import com.club.manager.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // 커스텀한 비즈니스 예외가 터졌을 때 낚아채는 곳
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handlerBusinessException(
        BusinessException ex
    ) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // 입력값 유효성 검사 실패 시 낚아채는 곳
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 첫 번째 발생한 에러 메시지만 추출
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorMessage));
    }
}
