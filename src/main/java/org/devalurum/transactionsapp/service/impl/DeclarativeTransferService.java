package org.devalurum.transactionsapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.devalurum.transactionsapp.exception.DeclarativeServiceException;
import org.devalurum.transactionsapp.repository.AccountRepository;
import org.devalurum.transactionsapp.service.TransferService;
import org.devalurum.transactionsapp.utils.Utils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.apache.commons.lang3.compare.ComparableUtils.is;


@Service
@Qualifier("declarativeService")
@RequiredArgsConstructor
@Slf4j
public class DeclarativeTransferService implements TransferService {

    private final AccountRepository repository;

    @Transactional(isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            readOnly = false,
            rollbackFor = Exception.class)
    @Override
    public void transferMoney(long fromId, long toId, double amount) {
        /**
         * Транзакция из двух шагов:
         * 1.) снятие денег у отправителя
         * 2.) пополнения у получателя
         */

        Account from = repository.findById(fromId)
                .orElseThrow(EntityNotFoundException::new);
        Account to = repository.findById(toId)
                .orElseThrow(EntityNotFoundException::new);

        withdraw(from, BigDecimal.valueOf(amount));
        deposit(to, BigDecimal.valueOf(amount));
    }


    private void withdraw(Account from, BigDecimal amount) {
        if (is(amount).lessThan(BigDecimal.ZERO)) {
            throw new DeclarativeServiceException("Error: Withdraw amount is invalid. for the Account: "
                    + from.getId() + " Name: " + from.getName());
        } else if (is(amount).greaterThan(from.getBalance())) {
            throw new DeclarativeServiceException(
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
            throw new DeclarativeServiceException(
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
