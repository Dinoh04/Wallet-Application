package org.example.tests;


import org.example.DAO.CurrencyCrudOperations;
import org.example.DatabaseConnection;
import org.example.Model.Currency;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperationsTest {
  public static void main(String[] args) {
    try(Connection connection = DatabaseConnection.getConnection()) {
      // Création de l'instance CurrencyCrudOperations
      CurrencyCrudOperations crudOperations = new CurrencyCrudOperations(connection);
      // Test de la méthode findAll()
      try {
        List<Currency> allCurrency = crudOperations.findAll();
        System.out.println("All Currency:");
        for (Currency c : allCurrency) {
          System.out.println(c);
        }
      } catch (SQLException e) {
        System.out.println("Error retrieving currencies: " + e.getMessage());
      }
      //Test de la méthode saveall()
      Currency currency1 = new Currency(null,"Euro", "EUR");
      Currency currency2 = new Currency(null,"Ariary", "AR");
      List<Currency> currenciesToSave = new ArrayList<>();
      currenciesToSave.add(currency1);
      currenciesToSave.add(currency2);
      try {
        List<Currency> savedCurrencies = crudOperations.saveAll(currenciesToSave);
        System.out.println("Currencies saved:");
        for (Currency currency : savedCurrencies) {
          System.out.println(currency);
        }
      } catch (SQLException e) {
        System.out.println("Error saving currencies: " + e.getMessage());
      }

      // Création d'un objet Currency à sauvegarder
      Currency currency = new Currency(null,"Euro", "EUR");

      // Test de la méthode save()
      try {
        Currency savedCurrency = crudOperations.save(currency);
        System.out.println("Currency saved: " + savedCurrency);
      } catch (SQLException e) {
        System.out.println("Error saving currency: " + e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }
}
