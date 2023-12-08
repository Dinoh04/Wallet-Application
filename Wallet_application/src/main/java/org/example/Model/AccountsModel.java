package org.example.Model;

import lombok.*;
import org.example.accountType;;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountsModel {
    private int idAccounts;
    private String accountsName;
    private Double accountsBalance;
    private LocalDate lastUpdate;
    private  int idCurrency;
    private accountType AccountType;

    private List<Transaction> transactions;


}
