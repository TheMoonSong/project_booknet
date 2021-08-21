package org.moonsong.booknet.exception;

import org.springframework.http.HttpStatus;

public class PasswordMissmatchException extends DomainLogicException {
    private static final String MESSAGE = "회원 정보를 잘못 입력하였습니다.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public PasswordMissmatchException() {
        super(MESSAGE, STATUS);
    }
}
