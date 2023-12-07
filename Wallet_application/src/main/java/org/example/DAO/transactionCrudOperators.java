package org.example.DAO;

import org.example.Model.Transaction;
import org.example.transactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class transactionCrudOperators implements CrudOperations <Transaction>{
    private Connection connection;

    public transactionCrudOperators(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Transaction> findAll() throws SQLException {
        List<Transaction> allTransaction = new ArrayList<>();
        String sql = "SELECT * FROM Transaction";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                allTransaction.add(new Transaction(
                        resultSet.getInt("idTransaction"),
                        resultSet.getString("label"),
                        resultSet.getDouble("amount"),
                        resultSet.getDate("transactionDate").toLocalDate(),
                        transactionType.valueOf(resultSet.getString("TransactionType")),
                        resultSet.getInt("idAccounts")
                ));
            }

        }
        return allTransaction;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) throws SQLException {
        String sql = "INSERT INTO Transaction (idTransaction, label, amount,transactionDate ,TransactionType, idAccounts) VALUES (?, ?, ?, ?, ?,?) " +
                "ON CONFLICT (label,amount,transactionDate,transactionType,idAccounts) DO NOTHING";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Transaction transaction : toSave) {
                preparedStatement.setInt(1, transaction.getIdTransaction());
                preparedStatement.setString(2, transaction.getLabel());
                preparedStatement.setDouble(3, transaction.getAmount());
                preparedStatement.setDate(4, Date.valueOf(transaction.getTransactionDate()));
                preparedStatement.setString(5, transaction.getTransactionType().name());
                preparedStatement.setInt(6, transaction.getIdAccounts());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }

        return toSave;
    }

    @Override
    public Transaction save(Transaction toSave) throws SQLException {
        return null;
    }

    @Override
    public List<Transaction> update(List<Transaction> toUpdate) throws SQLException {
        String sql = "UPDATE Transaction SET label = ?, amount = ?,transactionDate=?, TransactionType = ?, idAccounts = ? WHERE idTransaction = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Transaction transaction : toUpdate) {
                preparedStatement.setString(1, transaction.getLabel());
                preparedStatement.setDouble(2, transaction.getAmount());
                preparedStatement.setDate(3, Date.valueOf(transaction.getTransactionDate()));
                preparedStatement.setString(4, transaction.getTransactionType().name());
                preparedStatement.setString(5, transaction.getTransactionType().name());
                preparedStatement.setInt(6, transaction.getIdAccounts());

                preparedStatement.addBatch();
            }


            preparedStatement.executeBatch();
        }
        return toUpdate;
    }




    @Override
    public Transaction delete(Transaction toDelete) throws SQLException {
        String sql = "DELETE FROM Transaction  WHERE idTransaction = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, toDelete.getIdTransaction());
            preparedStatement.executeUpdate();
        }
        return toDelete;
    }
}
