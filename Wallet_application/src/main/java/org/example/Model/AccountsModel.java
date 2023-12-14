package org.example.Model;

import lombok.*;
import org.example.AccountType;
;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountsModel {
  private Integer idAccounts;
  private String accountsName;
  private Double accountsBalance;
  private LocalDate lastUpdate;
  private int idCurrency;
  private AccountType accountType;

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
