package org.moonsong.booknet.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends DomainLogicException {
    private static final String MESSAGE = "인증 유효기간이 지났습니다. 재로그인이 필요합니다.";
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public TokenExpiredException() {
        super(MESSAGE, STATUS);
    }
}
