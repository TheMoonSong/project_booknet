package org.moonsong.booknet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class DomainLogicException extends RuntimeException {
    private final HttpStatus status;

    public DomainLogicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public DomainLogicException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
