package org.devalurum.transactionsapp.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TransferRepository {

    void transfer(long fromId, long toId, double amount);

}
