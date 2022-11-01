package org.devalurum.transactionsapp.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProgrammaticServiceException extends RuntimeException {

    public ProgrammaticServiceException(Exception e) {
        super(e);
    }

    public ProgrammaticServiceException(String message) {
        super(message);
    }

    public ProgrammaticServiceException(String message, Exception e) {
        super(message, e);
    }
}