package org.example.Model;

import lombok.*;
import org.example.accountType;;

import java.time.LocalDate;

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
}
