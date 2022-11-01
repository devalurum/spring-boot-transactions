package org.devalurum.transactionsapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.domain.dto.AccountDto;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.devalurum.transactionsapp.domain.mapper.AccountMapper;
import org.devalurum.transactionsapp.repository.AccountRepository;
import org.devalurum.transactionsapp.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository repository;

    @Override
    public List<AccountDto> getAllAccounts() {
        return repository.findAll().stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Account addAccount(AccountDto accountDto) {
        if (repository.findById(accountDto.getId()).isPresent()) {
            throw new ValidationException("Account exists!");
        }

        return repository.save(accountMapper.toModel(accountDto));
    }

    @Override
    public AccountDto getAccount(long id) {
        return repository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }
}
