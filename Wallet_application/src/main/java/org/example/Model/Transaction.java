package org.example.Model;

import lombok.*;
import org.example.TransactionType;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Transaction {

    private Integer idTransaction;
    private String label;
    private Double amount;
    private LocalDate transactionDate;
    private TransactionType transactionType;
    private int idAccounts;
    private int idCategory;
}
