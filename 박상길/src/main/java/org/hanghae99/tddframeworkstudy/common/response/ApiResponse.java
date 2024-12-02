package org.hanghae99.tddframeworkstudy.common.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    /**
     * status
     */
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAIL = "fail";
    private static final String STATUS_ERROR = "error";

    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;


    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
            .status(STATUS_SUCCESS)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
            .status(STATUS_FAIL)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponse> error(String message) {
        ApiResponse response = ApiResponse.builder()
            .status(STATUS_ERROR)
            .message(message)
            .data(null)
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.ok(response);
    }

}
