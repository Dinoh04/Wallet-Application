package org.example.tests;

import org.example.AccountType;
import org.example.DAO.AccountsCrudOperations;
import org.example.DatabaseConnection;
import org.example.Main;
import org.example.Model.AccountsModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountsCrudOperationsTest {
  public static void main(String[] args) {
    try (Connection connection = DatabaseConnection.getConnection()) {
      // Création de l'instance AccountsCrudOperations
      AccountsCrudOperations crudOperations = new AccountsCrudOperations(connection);

      // Test de la méthode findAll()
      try {
        List<AccountsModel> allAccounts = crudOperations.findAll();
        System.out.println("All Accounts:");
        for (AccountsModel account : allAccounts) {
          System.out.println(account);
        }
      } catch (SQLException e) {
        System.out.println("Error retrieving accounts: " + e.getMessage());
      }

      // Création d'objets AccountsModel à sauvegarder
      AccountsModel account1 = new AccountsModel(
          null,
          "compte courant",
          1000.0,
          LocalDate.now(),
          1,
          AccountType.BANK
      );

      AccountsModel account2 = new AccountsModel(
          null,
          "compte épargne",
          2000.0,
          LocalDate.now(),
          2,
          AccountType.MOBILE_MONEY
      );
      AccountsModel account3 = new AccountsModel(
          null,
          "compte courant",
          3000.0,
          LocalDate.now(),
          1,
          AccountType.CASH
      );

      List<AccountsModel> accountsToSave = new ArrayList<>();
      accountsToSave.add(account1);
      accountsToSave.add(account2);
      accountsToSave.add(account3);
      // Test de la méthode saveAll()
      try {
        List<AccountsModel> savedAccounts = crudOperations.saveAll(accountsToSave);
        System.out.println("Accounts saved:");
        for (AccountsModel account : savedAccounts) {
          System.out.println(account);
        }
      } catch (SQLException e) {
        System.out.println("Error saving accounts: " + e.getMessage());
      }

      // Création d'un objet AccountsModel à sauvegarder
      AccountsModel account = new AccountsModel(
          null,
          "compte courant",
          3000.0,
          LocalDate.now(),
          3,
          AccountType.CASH
      );

      // Test de la méthode save()
      try {
        AccountsModel savedAccount = crudOperations.save(account);
        System.out.println("Account saved: " + savedAccount);
      } catch (SQLException e) {
        System.out.println("Error saving account: " + e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }
}
