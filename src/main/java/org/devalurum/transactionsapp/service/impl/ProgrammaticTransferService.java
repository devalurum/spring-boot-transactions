package org.devalurum.transactionsapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.devalurum.transactionsapp.exception.ProgrammaticServiceException;
import org.devalurum.transactionsapp.repository.AccountRepository;
import org.devalurum.transactionsapp.service.TransferService;
import org.devalurum.transactionsapp.utils.Utils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.apache.commons.lang3.compare.ComparableUtils.is;


@Service
@Qualifier("programmaticService")
@RequiredArgsConstructor
@Slf4j
public class ProgrammaticTransferService implements TransferService {

    private final AccountRepository repository;

    private final PlatformTransactionManager transactionManager;

    @Override
    public void transferMoney(long fromId, long toId, double amount) {
        /**
         * Транзакция из двух шагов:
         * 1.) снятие денег у отправителя
         * 2.) пополнения у получателя
         */

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        try {
            Account from = repository.findById(fromId)
                    .orElseThrow(EntityNotFoundException::new);
            Account to = repository.findById(toId)
                    .orElseThrow(EntityNotFoundException::new);
            withdraw(from, BigDecimal.valueOf(amount));
            deposit(to, BigDecimal.valueOf(amount));
            transactionManager.commit(transactionStatus);
        } catch (NoSuchElementException | EntityNotFoundException e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    private void withdraw(Account from, BigDecimal amount) {
        if (is(amount).lessThan(BigDecimal.ZERO)) {
            throw new ProgrammaticServiceException("Error: Withdraw amount is invalid. for the Account: "
                    + from.getId() + " Name: " + from.getName());
        } else if (is(amount).greaterThan(from.getBalance())) {
            throw new ProgrammaticServiceException(
                    "Error: Insufficient funds.\n Account: " + from.getId() + "\n" + "Requested:"
                            + Utils.numberFormatForBigDecimal(amount) + "Available: " + "\n" +
                            Utils.numberFormatForBigDecimal(from.getBalance()));
        } else {
            log.info("from Account id: {}, Debited: {}-{}", from.getId(),
                    Utils.numberFormatForBigDecimal(from.getBalance()), Utils.numberFormatForBigDecimal(amount));
            from.setBalance(from.getBalance().subtract(amount));
            from.setLastOperation("Debited");
            repository.save(from);
        }
    }

    private void deposit(Account to, BigDecimal amount) {
        if (is(amount).lessThan(BigDecimal.ZERO)) {
            throw new ProgrammaticServiceException(
                    "Error: Deposit amount is invalid." + to.getId() + " " + Utils.numberFormatForBigDecimal(amount));

        } else {
            log.info("from Account id: {}, Credited: {}+{}", to.getId(),
                    Utils.numberFormatForBigDecimal(to.getBalance()), Utils.numberFormatForBigDecimal(amount));
            to.setBalance(to.getBalance().add(amount));
            to.setLastOperation("Credited");
            repository.save(to);
        }
    }
}
