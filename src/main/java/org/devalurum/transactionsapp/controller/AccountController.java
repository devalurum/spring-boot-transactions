package org.devalurum.transactionsapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.devalurum.transactionsapp.domain.dto.AccountDto;
import org.devalurum.transactionsapp.domain.dto.ErrorDto;
import org.devalurum.transactionsapp.domain.dto.TransferRequestDto;
import org.devalurum.transactionsapp.domain.entity.Account;
import org.devalurum.transactionsapp.service.AccountService;
import org.devalurum.transactionsapp.service.TransferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "Accounts")
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Qualifier("declarativeService")
    @NonNull
    private final TransferService declarativeService;

    @Qualifier("programmaticService")
    @NonNull
    private final TransferService programmaticService;

    @Qualifier("progTemplateService")
    @NonNull
    private final TransferService progTemplateService;

    @Qualifier("persistContextService")
    @NonNull
    private final TransferService persistContextService;


    @Operation(
            summary = "Get all account",
            responses = @ApiResponse(responseCode = "200",
                    description = "Accounts",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountDto.class))))
    )
    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Operation(
            summary = "Get account by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account for requested ID", content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto user(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @Operation(
            summary = "Create new account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New account is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            }
    )
    @PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto) {
        final Account account = accountService.addAccount(accountDto);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Transfer money from to accounts (Declarative)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer money from to accounts successfully"),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            })
    @PostMapping(value = "declarative/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> declarativeTransfer(@RequestBody TransferRequestDto transferRequestDto) {

        declarativeService.transferMoney(transferRequestDto.getFromId(), transferRequestDto.getToId(),
                transferRequestDto.getAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            summary = "Transfer money from to accounts (with TransactionManager)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer money from to accounts successfully"),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            })
    @PostMapping(value = "programmatic/trxmanager/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> programmaticTransactionTransfer(
            @RequestBody TransferRequestDto transferRequestDto) {

        programmaticService.transferMoney(transferRequestDto.getFromId(), transferRequestDto.getToId(),
                transferRequestDto.getAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            summary = "Transfer money from to accounts (with TransactionTemplate)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer money from to accounts successfully"),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            })
    @PostMapping(value = "programmatic/trxtemplate/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> progTemplateTransactionTransfer(
            @RequestBody TransferRequestDto transferRequestDto) {

        progTemplateService.transferMoney(transferRequestDto.getFromId(), transferRequestDto.getToId(),
                transferRequestDto.getAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Transfer money from to accounts (with EntityManager)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transfer money from to accounts successfully"),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
            })
    @PostMapping(value = "programmatic/persistcntx/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> persistContextTransactionTransfer(
            @RequestBody TransferRequestDto transferRequestDto) {

        persistContextService.transferMoney(transferRequestDto.getFromId(), transferRequestDto.getToId(),
                transferRequestDto.getAmount());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
