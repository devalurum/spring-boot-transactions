package org.devalurum.transactionsapp.domain.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
@ToString
@Jacksonized
public class AccountDto {
    long id;

    @Digits(integer = 12, fraction = 2)
    double balance;

    @NotBlank
    @Size(min = 3, max = 254)
    String name;

    String lastOperation;
}
