package org.example.DAO;

import org.example.Model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CurrencyCrudOperations implements CrudOperations <Currency> {
    private Connection connection;

    public CurrencyCrudOperations(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        List<Currency> allCurrency = new ArrayList<>();
        String sql = "SELECT * FROM Currency";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allCurrency.add(new Currency(
                        resultSet.getInt("idCurrency"),
                        resultSet.getString("currencyName"),
                        resultSet.getString("currencyCode")
                ));
            }
        }
        return allCurrency;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) throws SQLException {
        String sql = "INSERT INTO currency (currencyName, currencyCode) VALUES (?, ?)" + "ON CONFLICT (currencyName, currencyCode) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (Currency currency : toSave){
                preparedStatement.setString(1,currency.getCurrencyName());
                preparedStatement.setString(2,currency.getCurrencyCode());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return toSave;
    }

    @Override
    public Currency save(Currency toSave) throws SQLException {
        String sql = "INSERT INTO Currency (currencyName, currencyCode) VALUES (?, ?)" + "ON CONFLICT (currencyName, currencyCode) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,toSave.getCurrencyName());
            preparedStatement.setString(2,toSave.getCurrencyCode());
            preparedStatement.executeUpdate();
        }
        return toSave;
    }

    @Override
    public List<Currency> update(List<Currency> toUpdate) throws SQLException {
        String sql = "UPDATE currency SET currencyName =?, currencyCode =? WHERE idCurrency = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (Currency currency : toUpdate){
                preparedStatement.setString(1,currency.getCurrencyName());
                preparedStatement.setString(2,currency.getCurrencyCode());
                preparedStatement.setInt(3,currency.getIdCurrency());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return  toUpdate;
    }

    @Override
    public Currency delete(Currency toDelete) throws SQLException {
        String sql = "DELETE FROM Currency WHERE id_currency = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,toDelete.getIdCurrency());
            preparedStatement.executeUpdate();
        }
        return toDelete;
    }
}
