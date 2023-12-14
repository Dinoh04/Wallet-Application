package org.example.DAO;

import org.example.Model.Transaction;
import org.example.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperators implements CrudOperations<Transaction> {
  private Connection connection;

  public TransactionCrudOperators(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Transaction> findAll() throws SQLException {
    List<Transaction> allTransaction = new ArrayList<>();
    String sql = "SELECT * FROM Transaction";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        allTransaction.add(new Transaction(
            resultSet.getInt("idTransaction"),
            resultSet.getString("label"),
            resultSet.getDouble("amount"),
            resultSet.getDate("transactionDate").toLocalDate(),
            TransactionType.valueOf(resultSet.getString("TransactionType")),
            resultSet.getInt("idAccounts")
        ));
      }

    }
    return allTransaction;
  }

  @Override
  public List<Transaction> saveAll(List<Transaction> toSave) throws SQLException {
    List<Transaction> savedList = new ArrayList<>();
    for (Transaction toSaved : toSave) {
      Transaction savedTransaction = save(toSaved);
      savedList.add(savedTransaction);
    }

    return savedList;
  }

  @Override
  public Transaction save(Transaction toSave) throws SQLException {
    if (toSave.getIdTransaction() == null) {
      // If ID is null, do an insert
      String insertSql = "INSERT INTO Transaction (label, amount, transactionDate, TransactionType, idAccounts) VALUES (?, ?, ?, ?, ?)" +
          "ON CONFLICT (label, amount, transactionDate, TransactionType, idAccounts) DO NOTHING;";
      try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
        insertStatement.setString(1, toSave.getLabel());
        insertStatement.setDouble(2, toSave.getAmount());
        insertStatement.setDate(3, Date.valueOf(toSave.getTransactionDate()));
        insertStatement.setObject(4, toSave.getTransactionType(),Types.OTHER);
        insertStatement.setInt(5, toSave.getIdAccounts());
        insertStatement.executeUpdate();

        try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            toSave.setIdTransaction(generatedKeys.getInt(1));
          }
        }
      }
    } else {
      // if ID is not null, do an update
      String updateSql = "UPDATE Transaction SET label = ?, amount = ?, transactionDate = ?, TransactionType = ?, idAccounts = ? WHERE idTransaction = ?";
      try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
        updateStatement.setString(1, toSave.getLabel());
        updateStatement.setDouble(2, toSave.getAmount());
        updateStatement.setDate(3, Date.valueOf(toSave.getTransactionDate()));
        updateStatement.setObject(4, toSave.getTransactionType(),Types.OTHER);
        updateStatement.setInt(5, toSave.getIdAccounts());
        updateStatement.setInt(6, toSave.getIdTransaction());
        updateStatement.executeUpdate();
      }
    }

    return toSave;
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