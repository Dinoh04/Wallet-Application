package org.example;

import org.example.DAO.AccountsCrudOperations;
import org.example.DAO.transactionCrudOperators;
import org.example.Model.AccountsModel;
import org.example.Model.BalanceHistoryEntry;
import org.example.Model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
      DatabaseConnection con = new DatabaseConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            try {

                AccountsCrudOperations accountsCrudOperations = new AccountsCrudOperations(connection);

                /*AccountsModel account = new AccountsModel(2,"Beta",5000.00,LocalDate.now(),1, accountType.Bank);
                accountsCrudOperations.save(account);

                Transaction transaction = new Transaction(2, "Achat de chaussure", 1050.0, LocalDate.now(), transactionType.Debit, 13);

                AccountsModel updatedAccount = accountsCrudOperations.doTransaction(account, transaction);
                transactionCrudOperators transactionCrudOperators = new transactionCrudOperators(connection);
                transactionCrudOperators.save(transaction);
                List<Transaction> transactionList = transactionCrudOperators.findAll();
                transaction = transactionList.get(0);

                System.out.println("Compte mis à jour : " + updatedAccount); */



                int accountId = 13;

                LocalDateTime startDateTime = LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0);
                LocalDateTime endDateTime = LocalDateTime.of(2023, Month.DECEMBER, 31, 23, 59);


                List<BalanceHistoryEntry> balanceHistory = accountsCrudOperations.getBalanceHistory(accountId, startDateTime, endDateTime);

                if (!balanceHistory.isEmpty()) {
                    for (BalanceHistoryEntry entry : balanceHistory) {
                        System.out.println("Date-heure : " + entry.getUpdateDateTime() + ", Solde : " + entry.getBalance());
                    }
                } else {
                    System.out.println("Aucun historique de solde trouvé pour le compte dans l'intervalle spécifié.");
                }


            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("La connexion à la base de données a échoué.");
        }


    }
}