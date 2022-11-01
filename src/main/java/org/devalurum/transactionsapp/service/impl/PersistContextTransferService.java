package org.devalurum.transactionsapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.repository.TransferRepository;
import org.devalurum.transactionsapp.service.TransferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("persistContextService")
@RequiredArgsConstructor
@Slf4j
public class PersistContextTransferService implements TransferService {

    private final TransferRepository repository;

    @Override
    public void transferMoney(long fromId, long toId, double amount) {
        repository.transfer(fromId, toId, amount);
    }
}
