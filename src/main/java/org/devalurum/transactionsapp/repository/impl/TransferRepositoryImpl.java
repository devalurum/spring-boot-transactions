package org.devalurum.transactionsapp.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.devalurum.transactionsapp.exception.EntityManagerTransferRepositoryException;
import org.devalurum.transactionsapp.repository.TransferRepository;
import org.devalurum.transactionsapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;

import static org.apache.commons.lang3.compare.ComparableUtils.is;

@Repository
@Slf4j
public class TransferRepositoryImpl implements TransferRepository {

    private final EntityManager em;

    /**
     * При использовании аннотации @PersistContext для EntityManager выпадает ошибка:
     * Not allowed to create transaction on shared EntityManager - use Spring transactions or EJB CMT instead
     * обойти это можно только так, я так понял.
     */

    @Autowired
    public TransferRepositoryImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void transfer(long fromId, long toId, double amount) {
        /**
         * Транзакция из двух шагов:
         * 1.) снятие денег у отправителя
         * 2.) пополнения у получателя
         */
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Account from = em.find(Account.class, fromId);
            Account to = em.find(Account.class, toId);

            withdraw(from, BigDecimal.valueOf(amount));
            deposit(to, BigDecimal.valueOf(amount));

            transaction.commit();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            transaction.rollback();
            throw e;
        }
    }

    private void withdraw(Account from, BigDecimal amount) {
        if (is(amount).lessThan(BigDecimal.ZERO)) {
            throw new EntityManagerTransferRepositoryException("Error: Withdraw amount is invalid. for the Account: "
                    + from.getId() + " Name: " + from.getName());
        } else if (is(amount).greaterThan(from.getBalance())) {
            throw new EntityManagerTransferRepositoryException(
                    "Error: Insufficient funds.\n Account: " + from.getId() + "\n" + "Requested:"
                            + Utils.numberFormatForBigDecimal(amount) + "Available: " + "\n" +
                            Utils.numberFormatForBigDecimal(from.getBalance()));
        } else {
            log.info("from Account id: {}, Debited: {}-{}", from.getId(),
                    Utils.numberFormatForBigDecimal(from.getBalance()), Utils.numberFormatForBigDecimal(amount));
            from.setBalance(from.getBalance().subtract(amount));
            from.setLastOperation("Debited");
            em.merge(from);
        }
    }

    private void deposit(Account to, BigDecimal amount) {
        if (is(amount).lessThan(BigDecimal.ZERO)) {
            throw new EntityManagerTransferRepositoryException(
                    "Error: Deposit amount is invalid." + to.getId() + " " + Utils.numberFormatForBigDecimal(amount));

        } else {
            log.info("from Account id: {}, Credited: {}+{}", to.getId(),
                    Utils.numberFormatForBigDecimal(to.getBalance()), Utils.numberFormatForBigDecimal(amount));
            to.setBalance(to.getBalance().add(amount));
            to.setLastOperation("Credited");
            em.merge(to);
        }
    }
}
