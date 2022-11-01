package org.devalurum.transactionsapp.domain.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Value
@Builder
@ToString
@Jacksonized
public class TransferRequestDto {

    @Range(min = 1)
    @Min(value = 1, message = "Id should not be less than 1")
    long fromId;

    @Range(min = 1)
    @Min(value = 1, message = "Id should not be less than 1")
    long toId;

    @DecimalMin(value = "0.01", inclusive = false)
    @Digits(integer = 12, fraction = 2)
    double amount;
}
