package org.example.DAO;

import org.example.Model.AccountsModel;
import org.example.Model.Transaction;
import org.example.accountType;
import org.example.transactionType;

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
        String sql = "SELECT * FROM accounts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allAccounts.add(new AccountsModel(
                        resultSet.getInt("idAccounts"),
                        resultSet.getString("accountsName"),
                        resultSet.getDouble("accountsBalance"),
                        resultSet.getDate("lastUpdate").toLocalDate(),
                        resultSet.getInt("idCurrency"),
                        accountType.valueOf(resultSet.getString("AccountType"))
                ));
            }
        }
        return allAccounts;
    }

    @Override
    public List<AccountsModel> saveAll(List<AccountsModel> toSave) throws SQLException {
        String sql = "INSERT INTO Accounts (accountsName, accountsBalance, lastUpdate, idCurrency, accountType) VALUES (?, ?, ?,?,?)" + "ON CONFLICT (AccountsName,accountsBalance,idCurrency,AccountType) DO NOTHING;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
            for (AccountsModel accountsModel : toSave){
               preparedStatement.setString(1,accountsModel.getAccountsName());
               preparedStatement.setDouble(2,accountsModel.getAccountsBalance());
               preparedStatement.setDate(3, Date.valueOf(accountsModel.getLastUpdate()));
               preparedStatement.setInt(4,accountsModel.getIdCurrency());
               //preparedStatement.setString(5,getTransactionType.name);
               preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return toSave;
    }

    @Override
    public AccountsModel save(AccountsModel toSave) throws SQLException {
        String sql = "INSERT INTO Accounts (accountsName, accountsBalance, lastUpdate, idCurrency, accountType) VALUES (?, ?, ?,?,?)" + "ON CONFLICT (AccountsName,accountsBalance,idCurrency,AccountType) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,toSave.getAccountsName());
            preparedStatement.setDouble(2,toSave.getAccountsBalance());
            preparedStatement.setDate(3, Date.valueOf(toSave.getLastUpdate()));
            preparedStatement.setInt(4,toSave.getIdCurrency());
            preparedStatement.setObject(5, toSave.getAccountType().name(),Types.OTHER);
            preparedStatement.executeUpdate();
        }
        return toSave;
    }

    @Override
    public List<AccountsModel> update(List<AccountsModel> toUpdate) throws SQLException {
        String sql = "UPDATE accounts SET accountsName = ?, accountsBalance= ? , lastUpdate = ?,idCurrency=?,accountType=?  WHERE idAccounts =?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (AccountsModel accountsModel : toUpdate){
                preparedStatement.setString(1,accountsModel.getAccountsName());
                preparedStatement.setDouble(2,accountsModel.getAccountsBalance());
                preparedStatement.setDate(3, Date.valueOf(accountsModel.getLastUpdate()));
                preparedStatement.setInt(4,accountsModel.getIdCurrency());
                preparedStatement.setString(5, String.valueOf(accountsModel.getAccountType()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return toUpdate;
    }

    @Override
    public AccountsModel delete(AccountsModel toDelete) throws SQLException {
        return null;
    }

    public AccountsModel performTransaction(AccountsModel account, Transaction transaction) {
        double updatedBalance;
        if (transaction.getTransactionType() == transactionType.Credit) {
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

        return null; // Ou une valeur par défaut, selon votre logique
    }
}
