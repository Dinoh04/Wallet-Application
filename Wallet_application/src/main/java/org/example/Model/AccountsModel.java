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

    public void transfer(AccountsModel targetAccount, double amount) {
        if (this.idAccounts == targetAccount.getIdAccounts()) {
            System.out.println("Le transfert vers le même compte n'est pas autorisé.");
            return;
        }

        if (this.accountsBalance < amount) {
            System.out.println("Solde insuffisant pour effectuer le transfert.");
            return;
        }
    }

}
