package org.devalurum.transactionsapp.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeclarativeServiceException extends RuntimeException {

    public DeclarativeServiceException(Exception e) {
        super(e);
    }

    public DeclarativeServiceException(String message) {
        super(message);
    }

    public DeclarativeServiceException(String message, Exception e) {
        super(message, e);
    }
}