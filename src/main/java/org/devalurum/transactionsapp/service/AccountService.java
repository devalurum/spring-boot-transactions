package org.devalurum.transactionsapp.service;

import org.devalurum.transactionsapp.domain.dto.AccountDto;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AccountService {

    Account addAccount(AccountDto accountDto);

    List<AccountDto> getAllAccounts();

    AccountDto getAccount(long accountId);

}
