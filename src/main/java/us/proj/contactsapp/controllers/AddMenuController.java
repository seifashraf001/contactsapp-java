package us.proj.contactsapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import us.proj.contactsapp.ContactsApp;
import us.proj.contactsapp.db.DatabaseConnection;
import us.proj.contactsapp.objects.Validator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMenuController implements Initializable {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button createBtn;
    @FXML
    public TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField groupField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button clearBtn;

    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String groupName;

    Connection connection = null;
    Stage stage;

    private Boolean isDataValid(String name, String phoneNumber, String groupName, String email) {
        Boolean isValidName = Validator.isValidName(name);
        Boolean isValidPhoneNumber = Validator.isValidPhoneNumber(phoneNumber);
        Boolean isValidGroup = Validator.isValidName(groupName);
        Boolean isValidEmail = Validator.isEmailValid(email);

        if (isValidName && isValidPhoneNumber && isValidGroup && isValidEmail) {
            return true;
        } else {
            if (!isValidName) {
                nameField.clear();
            }
            if (!isValidPhoneNumber) {
                phoneNumberField.clear();
            }
            if (!isValidGroup) {
                groupField.clear();
            }
            if (!isValidEmail) {
                emailField.clear();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the contact data properly.");
            stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(ContactsApp.getLogo());
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    void createContact(ActionEvent event) {
        if (event.getSource().equals(createBtn)) {
            name = nameField.getText(); // these lines get the values from the text fields and saves them into variables that will be passed to the insertion query.
            phoneNumber = phoneNumberField.getText();
            address = addressField.getText();
            email = emailField.getText();
            groupName = groupField.getText();

            if (isDataValid(name, phoneNumber, address, email)) {
                try {
                    Connection connection = DatabaseConnection.connect();
                    DatabaseConnection.insertData(name, phoneNumber, address, email, groupName); // inserts data into the database using the method insertData() from the DatabaseConnection class.

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("User added successfully!");
                    alert.setHeaderText(null);
                    stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(ContactsApp.getLogo());
                    alert.showAndWait();

                    Stage stage = (Stage) createBtn.getScene().getWindow();
                    stage.close();
                }  catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) {
                            connection.close(); // closes connection after the operation is done.
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @FXML
    void cancelMenu(ActionEvent event) {
        if (event.getSource().equals(cancelBtn)) {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void clearMenu(ActionEvent event) {
        if (event.getSource().equals(clearBtn)) {
            nameField.clear();
            phoneNumberField.clear();
            addressField.clear();
            groupField.clear();
            emailField.clear();
            groupField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
