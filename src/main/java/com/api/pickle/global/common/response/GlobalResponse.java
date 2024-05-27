package com.api.pickle.global.common.response;

import com.api.pickle.global.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GlobalResponse {
    private boolean success;
    private int status;
    private Object data;
    private LocalDateTime timestamp;

    public static GlobalResponse success(int status, Object data) {
        return GlobalResponse.builder()
                .success(true)
                .status(status)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static GlobalResponse fail(int status, ErrorResponse errorResponse) {
        return GlobalResponse.builder()
                .success(false)
                .status(status)
                .data(errorResponse)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
