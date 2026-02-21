package com.club.manager.common.response;

public record ApiResponse<T>(
    String code,
    String message,
    T data
) {
    // 1. 성공했을 때 데이터를 담아 보내는 정적 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>("SUCCESS", "성공적으로 처리되었습니다.", data);
    }

    // 2. 에러가 났을 때 에러코드와 메세지만 담아 보내는 정적 메서드 (데이터는 null)
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<T>(code, message, null);
    }
}
