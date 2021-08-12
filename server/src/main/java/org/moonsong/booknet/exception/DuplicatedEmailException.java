package org.moonsong.booknet.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedEmailException extends CustomException {
    private static final String MESSAGE = "중복된 이메일입니다.";

    public DuplicatedEmailException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
