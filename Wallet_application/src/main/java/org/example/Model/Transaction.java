package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.transaction_type;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private int id_transaction;
    private String description;
    private float amount;
    private transaction_type transactionType;
    private int id_accounts;
}
