package org.devalurum.transactionsapp.service;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TransferService {

    void transferMoney(long fromId, long toId, double amount);

}
