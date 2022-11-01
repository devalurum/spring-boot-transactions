package org.devalurum.transactionsapp.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProgTemplateServiceException extends RuntimeException {

    public ProgTemplateServiceException(Exception e) {
        super(e);
    }

    public ProgTemplateServiceException(String message) {
        super(message);
    }

    public ProgTemplateServiceException(String message, Exception e) {
        super(message, e);
    }
}