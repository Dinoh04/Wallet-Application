package org.example.Repository;

import org.example.Model.AccountsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                        resultSet.getInt("id_accounts"),
                        resultSet.getString("accounts_name"),
                        resultSet.getFloat("accounts_balance"),
                        resultSet.getInt("id_currency")
                ));
            }
        }
        return allAccounts;
    }

    @Override
    public List<AccountsModel> saveAll(List<AccountsModel> toSave) throws SQLException {
        String sql = "INSERT INTO accounts (accounts_name, accounts_balance, id_currency) VALUES (?, ?, ?)" + "ON CONFLICT (accounts_name, id_currency) DO NOTHING;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
            for (AccountsModel accountsModel : toSave){
               preparedStatement.setString(1,accountsModel.getAccounts_name());
               preparedStatement.setFloat(2,accountsModel.getAccounts_balance());
               preparedStatement.setInt(3,accountsModel.getId_currency());
               preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return toSave;
    }

    @Override
    public AccountsModel save(AccountsModel toSave) throws SQLException {
        String sql = "INSERT INTO accounts (accounts_name, accounts_balance, id_currency) VALUES (?, ?, ?)" + "ON CONFLICT (accounts_name, id_currency) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,toSave.getAccounts_name());
            preparedStatement.setFloat(2,toSave.getAccounts_balance());
            preparedStatement.setInt(3,toSave.getId_currency());
            preparedStatement.executeUpdate();
        }
        return toSave;
    }

    @Override
    public List<AccountsModel> update(List<AccountsModel> toUpdate) throws SQLException {
        String sql = "UPDATE accounts SET accounts_name = ?, accounts_balance= ? , id_currency = ? WHERE id_accounts =?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (AccountsModel accountsModel : toUpdate){
                preparedStatement.setString(1,accountsModel.getAccounts_name());
                preparedStatement.setFloat(2,accountsModel.getAccounts_balance());
                preparedStatement.setInt(3,accountsModel.getId_currency());
                preparedStatement.setInt(4,accountsModel.getId_accounts());
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
}
