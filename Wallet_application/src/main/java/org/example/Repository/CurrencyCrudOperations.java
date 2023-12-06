package org.example.Repository;

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
        String sql = "SELECT * FROM currency";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allCurrency.add(new Currency(
                        resultSet.getInt("id_currency"),
                        resultSet.getString("currency_name"),
                        resultSet.getString("currency_symbole")
                ));
            }
        }
        return allCurrency;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) throws SQLException {
        String sql = "INSERT INTO currency (currency_name, currency_symbole) VALUES (?, ?)" + "ON CONFLICT (currency_name,currency_symbole) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (Currency currency : toSave){
                preparedStatement.setString(1,currency.getCurrency_name());
                preparedStatement.setString(2,currency.getCurrency_symbole());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return toSave;
    }

    @Override
    public Currency save(Currency toSave) throws SQLException {
        String sql = "INSERT INTO currency (currency_name, currency_symbole) VALUES (?, ?)" + "ON CONFLICT (currency_name,currency_symbole) DO NOTHING;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,toSave.getCurrency_name());
            preparedStatement.setString(2,toSave.getCurrency_symbole());
            preparedStatement.executeUpdate();
        }
        return toSave;
    }

    @Override
    public List<Currency> update(List<Currency> toUpdate) throws SQLException {
        String sql = "UPDATE currency SET currency_name =?, currency_symbole =? WHERE id_currency = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            for (Currency currency : toUpdate){
                preparedStatement.setString(1,currency.getCurrency_name());
                preparedStatement.setString(2,currency.getCurrency_symbole());
                preparedStatement.setInt(3,currency.getId_currency());

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return  toUpdate;
    }

    @Override
    public Currency delete(Currency toDelete) throws SQLException {
        String sql = "DELETE FROM currency WHERE id_currency = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,toDelete.getId_currency());
            preparedStatement.executeUpdate();
        }
        return toDelete;
    }
}
