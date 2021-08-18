package org.moonsong.booknet.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationHeaderUninvolvedException extends DomainLogicException {
    private static final String MESSAGE = "인증 정보가 누락되었습니다.";
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public AuthorizationHeaderUninvolvedException() {
        super(MESSAGE, STATUS);
    }
}
