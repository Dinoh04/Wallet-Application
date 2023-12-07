package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
        private static Connection connection;
        public DatabaseConnection(){

            try{
                connection =  DriverManager.getConnection(System.getenv("URL"),System.getenv("DB_USER"),System.getenv("DB_PASSWORD")

                );

                System.out.println("Connection successfully");
            }catch (SQLException e){
                System.out.println("Error while connection to the database "+" "+e.getMessage());
            }
        }

        public  static Connection getConnection(){
            if(connection == null){
                new DatabaseConnection();
            }

            return connection;
        }

    public static void main(String[] args) {
        DatabaseConnection con = new DatabaseConnection();
    }
    }


