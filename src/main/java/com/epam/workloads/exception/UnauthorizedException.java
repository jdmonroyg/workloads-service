package com.epam.workloads.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
