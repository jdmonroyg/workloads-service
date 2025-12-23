package com.epam.workloads.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
