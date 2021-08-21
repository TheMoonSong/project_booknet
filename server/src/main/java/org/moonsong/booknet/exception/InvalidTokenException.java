package org.moonsong.booknet.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends DomainLogicException {
    private static final String MESSAGE = "인증 정보가 유효하지 않습니다. 재로그인이 필요합니다.";
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public InvalidTokenException() {
        super(MESSAGE, STATUS);
    }
}
