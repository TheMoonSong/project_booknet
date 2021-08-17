package org.moonsong.booknet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String message;

    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse from(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
