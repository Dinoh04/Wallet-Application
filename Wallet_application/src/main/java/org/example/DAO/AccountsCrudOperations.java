package org.example.DAO;

import org.example.Model.AccountsModel;
import org.example.Model.BalanceHistoryEntry;
import org.example.Model.Transaction;
import org.example.AccountType;
import org.example.Model.TransferHistory;
import org.example.TransactionType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountsCrudOperations implements CrudOperations <AccountsModel>{
    private Connection connection;

    public AccountsCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<AccountsModel> findAll() throws SQLException {
        List<AccountsModel> allAccounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allAccounts.add(new AccountsModel(
                        resultSet.getInt("idAccounts"),
                        resultSet.getString("accountsName"),
                        resultSet.getDouble("accountsBalance"),
                        resultSet.getDate("lastUpdate").toLocalDate(),
                        resultSet.getInt("idCurrency"),
                        AccountType.valueOf(resultSet.getString("AccountType"))
                ));
            }
        }
        return allAccounts;
    }

    @Override
    public List<AccountsModel> saveAll(List<AccountsModel> toSave) throws SQLException {
       List<AccountsModel> savedList = new ArrayList<>();
       for (AccountsModel toSaved : toSave){
           AccountsModel savedAccount = save(toSaved);
           savedList.add(savedAccount);
       }
       return savedList;
    }

    @Override
    public AccountsModel save(AccountsModel toSave) throws SQLException {
        if (toSave.getIdAccounts() == null) {
            // If ID is null, do an insert
            String insertSql = "INSERT INTO Accounts (accountsName, accountsBalance, lastUpdate, idCurrency, accountType) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setString(1, toSave.getAccountsName());
                insertStatement.setDouble(2, toSave.getAccountsBalance());
                insertStatement.setDate(3, Date.valueOf(toSave.getLastUpdate()));
                insertStatement.setInt(4, toSave.getIdCurrency());
                insertStatement.setObject(5, toSave.getAccountType(), Types.OTHER);
                insertStatement.executeUpdate();

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        toSave.setIdAccounts(generatedKeys.getInt(1));
                    }
                }
            }
        } else {
            // if idAccounts  is not null, do an update
            String updateSql = "UPDATE Accounts SET accountsName = ?, accountsBalance = ?, lastUpdate = ?, idCurrency = ?, accountType = ? WHERE idAccounts = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setString(1, toSave.getAccountsName());
                updateStatement.setDouble(2, toSave.getAccountsBalance());
                updateStatement.setDate(3, Date.valueOf(toSave.getLastUpdate()));
                updateStatement.setInt(4, toSave.getIdCurrency());
                updateStatement.setObject(5, toSave.getAccountType(), Types.OTHER);
                updateStatement.setInt(6, toSave.getIdAccounts());
                updateStatement.executeUpdate();
            }
        }

        return toSave;
    }
    @Override
    public AccountsModel delete(AccountsModel toDelete) throws SQLException {
        return null;
    }

    public AccountsModel doTransaction(AccountsModel account, Transaction transaction) {
        double updatedBalance;
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            updatedBalance = account.getAccountsBalance() + transaction.getAmount();
        } else {
            updatedBalance = account.getAccountsBalance() - transaction.getAmount();
        }


        account.setAccountsBalance(updatedBalance);


        account.setLastUpdate(LocalDate.now());

        return account;
    }
    public Double getBalanceAtDateTime(int accountId, LocalDateTime dateTime) throws SQLException {
        String sql = "SELECT accountsBalance FROM accounts WHERE idAccounts = ? AND lastUpdate <= ? ORDER BY lastUpdate DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dateTime));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("accountsBalance");
            }
        }

        return null;
    }

    public List<BalanceHistoryEntry> getBalanceHistory(int accountId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        List<BalanceHistoryEntry> balanceHistory = new ArrayList<>();
        String sql = "SELECT lastUpdate, accountsBalance FROM accounts WHERE idAccounts = ? AND lastUpdate >= ? AND lastUpdate <= ? ORDER BY lastUpdate";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startDateTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(endDateTime));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LocalDateTime updateDateTime = resultSet.getTimestamp("lastUpdate").toLocalDateTime();
                Double balance = resultSet.getDouble("accountsBalance");

                BalanceHistoryEntry historyEntry = new BalanceHistoryEntry(updateDateTime, balance);
                balanceHistory.add(historyEntry);
            }
        }

        return balanceHistory;
    }

    public  TransferHistory save(TransferHistory transferHistory) throws SQLException {
        String sql = "INSERT INTO TransferHistory (debitTransactionId, creditTransactionId, transferDate) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, transferHistory.getDebitTransactionId());
            preparedStatement.setInt(2, transferHistory.getCreditTransactionId());
            preparedStatement.setTimestamp(3, transferHistory.getTransferDate());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transferHistory.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Échec de la création de l'historique de transfert, aucun ID généré.");
            }
        }
        return transferHistory;
    }


    public List<TransferHistory> getTransferHistoryByDateInterval(Timestamp startDate, Timestamp endDate) throws SQLException {
        List<TransferHistory> transferHistories = new ArrayList<>();
        String sql = "SELECT * FROM TransferHistory WHERE transferDate BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, startDate);
            preparedStatement.setTimestamp(2, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TransferHistory transferHistory = new TransferHistory(
                        resultSet.getInt("id"),
                        resultSet.getInt("debitTransactionId"),
                        resultSet.getInt("creditTransactionId"),
                        resultSet.getTimestamp("transferDate")
                );
                transferHistories.add(transferHistory);
            }
        }

        return transferHistories;
    }

}
