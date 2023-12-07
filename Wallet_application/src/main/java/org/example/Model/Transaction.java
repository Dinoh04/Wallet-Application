package org.example.Model;

import lombok.*;
import org.example.transactionType;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Transaction {

    private int idTransaction;
    private String label;
    private Double amount;
    private LocalDate transactionDate;
    private transactionType TransactionType;
    private int idAccounts;
}
