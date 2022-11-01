package org.devalurum.transactionsapp.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityManagerTransferRepositoryException extends RuntimeException {

    public EntityManagerTransferRepositoryException(Exception e) {
        super(e);
    }

    public EntityManagerTransferRepositoryException(String message) {
        super(message);
    }

    public EntityManagerTransferRepositoryException(String message, Exception e) {
        super(message, e);
    }
}