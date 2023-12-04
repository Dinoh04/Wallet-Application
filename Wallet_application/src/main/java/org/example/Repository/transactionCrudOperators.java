package org.example.Repository;

import org.example.Model.Transaction;
import org.example.transaction_type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "SELECT * FROM Transaction ";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                allTransaction.add(new Transaction(
                        resultSet.getInt("id_transaction"),
                        resultSet.getString("description"),
                        resultSet.getFloat("amount"),
                        transaction_type.valueOf(resultSet.getString("transaction_type")),
                        resultSet.getInt("id_accounts")
                ));
            }

        }
        return allTransaction;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) throws SQLException {
        String sql = "INSERT INTO transaction (id_transaction, description, amount, transaction_type, id_accounts) VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id_transaction) DO NOTHING";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Transaction transaction : toSave) {
                preparedStatement.setInt(1, transaction.getId_transaction());
                preparedStatement.setString(2, transaction.getDescription());
                preparedStatement.setDouble(3, transaction.getAmount());
                preparedStatement.setString(4, transaction.getTransactionType().name());
                preparedStatement.setInt(5, transaction.getId_accounts());

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
    public Transaction update(Transaction ToUpdate) throws SQLException {
        return null;
    }

    @Override
    public Transaction delete(Transaction toDelete) throws SQLException {
        return null;
    }
}
