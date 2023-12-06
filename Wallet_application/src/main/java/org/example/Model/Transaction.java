package org.example.Model;

import lombok.*;
import org.example.transactionType;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Transaction {

    private int idTransaction;
    private String label;
    private Double amount;
    private LocalDateTime transactionDate;
    private transactionType TransactionType;
    private int idAccounts;
}
