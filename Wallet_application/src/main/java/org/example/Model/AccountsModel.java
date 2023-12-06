package org.example.Model;

import lombok.*;
import org.example.Repository.accountType;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountsModel {
    private int idAccounts;
    private String accountsName;
    private Double accountsBalance;
    private LocalDateTime lastUpdate;
    private  int idCurrency;
    private accountType AccountType;
}
