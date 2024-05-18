package us.proj.contactsapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;
    static String insertQuery = "INSERT INTO contacts (name, phoneNumber, address, email, groupName) VALUES (?, ?, ?, ?, ?)"; // contactID is incremented automatically in the database each time a new row is inserted
    static String updateQuery = "UPDATE contacts SET name=?, phoneNumber=?, address=?, email=?, groupName=? WHERE contactID=?";
    static PreparedStatement preparedStatement;

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactsApp", "root", "");
            System.out.println("Connected Successfully!");
        } catch (Exception e) {
            throw new SQLException("Error connecting to the database", e);
        }
        return connection;
    }

    public static void insertData(String name, String phoneNumber, String address, String email, String groupName) {
            try {
                preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, groupName);

                preparedStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void updateData(int id, String name, String phoneNumber, String address, String email, String groupName) {
        try {
            connection = connect();
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, groupName);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();

            System.out.println("Data updated successfully"); // for debugging.
        } catch (Exception e) {
            System.out.println("Error updating data: " + e);
        }
    }
}