package org.example.Model;

import lombok.*;
import org.example.AccountType;
import org.example.DAO.AccountsCrudOperations;
import org.example.DAO.TransactionCrudOperators;
import org.example.TransactionType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import static org.example.DatabaseConnection.getConnection;

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

  public void transfer(AccountsModel targetAccount, double amount) throws SQLException {
    if (this.idAccounts == targetAccount.getIdAccounts()) {
      System.out.println("Erreur : Le transfert vers le même compte n'est pas autorisé.");
      return;
    }

    if (this.accountsBalance < amount) {
      System.out.println("Erreur : Solde insuffisant pour effectuer le transfert.");
      return;
    }

    this.accountsBalance -= amount;
    targetAccount.accountsBalance += amount;
    Transaction debitTransaction = new Transaction(1,"Vendre voiture",1000000.0, LocalDate.now(), TransactionType.CREDIT,1); // Créer une transaction de débit
    Transaction creditTransaction = new Transaction(2,"achat de chaussure",1000.0, LocalDate.now(),TransactionType.DEBIT,2); // Créer une transaction de crédit

    // Enregistrer les transactions dans la base de données
    TransactionCrudOperators transactionCrudOperators = new TransactionCrudOperators(getConnection());
    transactionCrudOperators.save(debitTransaction);
    transactionCrudOperators.save(creditTransaction);

    TransferHistory transferHistory = new TransferHistory(1,1,1,null);
    transferHistory.setDebitTransactionId(debitTransaction.getIdTransaction());
    transferHistory.setCreditTransactionId(creditTransaction.getIdTransaction());
    transferHistory.setTransferDate(Timestamp.from(Instant.now())); // Utilisez la date actuelle

    // Enregistrer transferHistory dans la base de données
    AccountsCrudOperations transferHistoryCrudOperations = new AccountsCrudOperations(getConnection());
    transferHistoryCrudOperations.save(transferHistory);
  }
}
