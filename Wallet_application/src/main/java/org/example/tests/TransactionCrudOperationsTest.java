package org.example.tests;

import org.example.DAO.TransactionCrudOperators;
import org.example.DatabaseConnection;
import org.example.Model.Transaction;
import org.example.TransactionType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperationsTest {
  public static void main(String[] args) {
    try (Connection connection = DatabaseConnection.getConnection()) {
      // Création de l'instance TransactionCrudOperators
      TransactionCrudOperators crudOperators = new TransactionCrudOperators(connection);

      // Test de la méthode findAll()
      try {
        List<Transaction> allTransactions = crudOperators.findAll();
        System.out.println("All Transactions:");
        for (Transaction transaction : allTransactions) {
          System.out.println(transaction);
        }
      } catch (SQLException e) {
        System.out.println("Error retrieving transactions: " + e.getMessage());
      }

      // Test de la méthode saveAll()
      Transaction transaction1 = new Transaction(null, "Purchase", 100.0, java.time.LocalDate.now(), TransactionType.CREDIT, 1);
      Transaction transaction2 = new Transaction(null, "Salary", 2000.0, java.time.LocalDate.now(), TransactionType.DEBIT, 2);
      List<Transaction> transactionsToSave = new ArrayList<>();
      transactionsToSave.add(transaction1);
      transactionsToSave.add(transaction2);
      try {
        List<Transaction> savedTransactions = crudOperators.saveAll(transactionsToSave);
        System.out.println("Transactions saved:");
        for (Transaction savedTransaction : savedTransactions) {
          System.out.println(savedTransaction);
        }
      } catch (SQLException e) {
        System.out.println("Error saving transactions: " + e.getMessage());
      }

      // Test de la méthode save()
      Transaction transactionToSave = new Transaction(null, "Online Shopping", 50.0, java.time.LocalDate.now(), TransactionType.CREDIT, 3);
      try {
        Transaction savedTransaction = crudOperators.save(transactionToSave);
        System.out.println("Transaction saved: " + savedTransaction);
      } catch (SQLException e) {
        System.out.println("Error saving transaction: " + e.getMessage());
      }

      // Test de la méthode delete()
      try {
        Transaction transactionToDelete = new Transaction(1, "", 0.0, java.time.LocalDate.now(), TransactionType.DEBIT, 3);
        Transaction deletedTransaction = crudOperators.delete(transactionToDelete);
        System.out.println("Transaction deleted: " + deletedTransaction);
      } catch (SQLException e) {
        System.out.println("Error deleting transaction: " + e.getMessage());
      }

    } catch (SQLException e) {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }
}
