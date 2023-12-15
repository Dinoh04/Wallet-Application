package org.example;

import org.example.DAO.AccountsCrudOperations;
import org.example.DAO.TransactionCrudOperators;
import org.example.Model.AccountsModel;
import org.example.Model.BalanceHistoryEntry;
import org.example.Model.Transaction;
import org.example.Model.TransferHistory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;


public class Main {
    public static void main(String[] args) throws SQLException {
      DatabaseConnection con = new DatabaseConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            try {

                AccountsCrudOperations accountsCrudOperations = new AccountsCrudOperations(connection);

                AccountsModel account = new AccountsModel(null,"Beta",5000.00,LocalDate.now(),1, AccountType.BANK);
                AccountsModel account2 = new AccountsModel(null,"omega",8000.400,LocalDate.now(),1,AccountType.CASH);


                Transaction transaction = new Transaction(null, "Achat de chaussure", 1050.0, LocalDate.now(), TransactionType.DEBIT, 1,1);

                AccountsModel updatedAccount = accountsCrudOperations.doTransaction(account, transaction);
                TransactionCrudOperators transactionCrudOperators = new TransactionCrudOperators(connection);
                transactionCrudOperators.save(transaction);
                List<Transaction> transactionList = transactionCrudOperators.findAll();
                transaction = transactionList.get(0);

                System.out.println("Compte mis à jour : " + updatedAccount);

/*
                Transaction debitTransaction = new Transaction(1,"Vendre voiture",1000000.0, LocalDate.now(),TransactionType.CREDIT,1); // Créer une transaction de débit
                Transaction creditTransaction1 = new Transaction(null,"achat de chaussure",1000.0, LocalDate.now(),TransactionType.DEBIT,1); // Créer une transaction de crédit

                // Enregistrer les transactions dans la base de données
                TransactionCrudOperators transactionCrudOperators1 = new TransactionCrudOperators(connection);
                transactionCrudOperators.save(debitTransaction);
                transactionCrudOperators.save(creditTransaction1);

                TransferHistory transferHistory = new TransferHistory(1,1,1,null);
                transferHistory.setDebitTransactionId(debitTransaction.getIdTransaction());
                transferHistory.setCreditTransactionId(creditTransaction1.getIdTransaction());
                transferHistory.setTransferDate(Timestamp.from(Instant.now())); // Utilisez la date actuelle

                // Enregistrer transferHistory dans la base de données
                AccountsCrudOperations transferHistoryCrudOperations = new AccountsCrudOperations(connection);
                transferHistoryCrudOperations.save(transferHistory);


                // Exemple de transfert entre les deux comptes
                account.transfer(account2, 100.0);

                // Exemple de récupération de l'historique des transferts dans une intervalle de date donnée
                AccountsCrudOperations transferHere = new AccountsCrudOperations(connection);
                Timestamp startDate = Timestamp.valueOf("2023-01-01 00:00:00");
                Timestamp endDate = Timestamp.valueOf("2023-12-31 23:59:59");
                List<TransferHistory> transferHistoryList = transferHistoryCrudOperations.getTransferHistoryByDateInterval(startDate,endDate);

                // Afficher l'historique des transferts
                for (TransferHistory TransferHistory : transferHistoryList) {
                    System.out.println("ID : " + transferHistory.getId());
                    System.out.println("Debit Transaction ID : " + transferHistory.getDebitTransactionId());
                    System.out.println("Credit Transaction ID : " + transferHistory.getCreditTransactionId());
                    System.out.println("Transfer Date : " + transferHistory.getTransferDate());
                    System.out.println("------------------------------");
                }

                */


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