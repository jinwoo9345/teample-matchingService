package com.teample.matching.global.common.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T>{

    private final int status;       // HTTP 상태 코드 (예: 200, 400, 404)
    private final String message;   // 결과 메시지
    private final T data;           // 실제 데이터 (DTO, List 등)

    // 성공 응답을 만들 때 사용하는 편의 메소드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "성공", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    // 에러 응답을 만들 때 사용하는 편의 메소드
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }

}
