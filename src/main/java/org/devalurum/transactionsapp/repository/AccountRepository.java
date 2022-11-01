package org.devalurum.transactionsapp.repository;

import org.devalurum.transactionsapp.domain.entity.Account;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Account.class, idClass = Long.class)
public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(long id);

    Account save(Account account);
}