package org.devalurum.transactionsapp.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    @Digits(integer = 12, fraction = 2)
    BigDecimal balance;

    @NotBlank
    String name;

    String lastOperation;
}
