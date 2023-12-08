package org.example;

import org.example.DAO.AccountsCrudOperations;
import org.example.DAO.transactionCrudOperators;
import org.example.Model.AccountsModel;
import org.example.Model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
      DatabaseConnection con = new DatabaseConnection();
        Connection connection = con.getConnection();

        if (connection != null) {
            try {

                AccountsCrudOperations accountsCrudOperations = new AccountsCrudOperations(connection);

                AccountsModel account = new AccountsModel(1,"Alpha",1000.00,LocalDate.now(),1, accountType.Cash);
                accountsCrudOperations.save(account);


                List<AccountsModel> accountsList = accountsCrudOperations.findAll();
                account = accountsList.get(0);
                Transaction transaction = new Transaction(1, "Achat en ligne", 50.0, LocalDate.now(), transactionType.Debit, 1);

                AccountsModel updatedAccount = accountsCrudOperations.performTransaction(account, transaction);

                System.out.println("Compte mis à jour : " + updatedAccount);

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