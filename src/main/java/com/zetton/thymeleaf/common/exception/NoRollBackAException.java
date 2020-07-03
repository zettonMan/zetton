package com.zetton.thymeleaf.common.exception;

public class NoRollBackAException extends RuntimeException {
    public NoRollBackAException() {
        super();
    }
    public NoRollBackAException(String runtime) {
        super(runtime);
    }
}