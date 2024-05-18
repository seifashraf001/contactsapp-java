package us.proj.contactsapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import us.proj.contactsapp.ContactsApp;
import us.proj.contactsapp.EditContactGui;
import us.proj.contactsapp.db.DatabaseConnection;
import us.proj.contactsapp.objects.Contact;
import us.proj.contactsapp.objects.Validator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class EditMenuController{

    @FXML
    private Button cancelBtn;
    @FXML
    private Button editBtn;
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

    private Contact initialData;
    Stage stage;


    public EditMenuController(Contact initialData) { // a constructor to get the initialData from the row and put it in the new GUI
        this.initialData = initialData; // not the selectedData will equal the initialData.
    }

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
    void initialize() {
        if (initialData != null) {
            nameField.setText(initialData.getName());
            phoneNumberField.setText(initialData.getPhoneNumber());
            addressField.setText(initialData.getAddress());
            emailField.setText(initialData.getEmail());
            groupField.setText(initialData.getGroupName());
        } else {
            System.out.println("Initial data is null"); // for debugging.
        }
    }

    @FXML
    void editContact(ActionEvent event) {
        if (event.getSource().equals(editBtn)) {
            String editedName = nameField.getText();
            String editedPhoneNumber = phoneNumberField.getText();
            String editedAddress = addressField.getText();
            String editedEmail = emailField.getText();
            String editedGroupName = groupField.getText();

            if (initialData != null) {
                if (isDataValid(editedName, editedPhoneNumber, editedAddress, editedEmail)) {
                    DatabaseConnection.updateData( // this method updates data on the Database
                            initialData.getContactID(),
                            editedName,
                            editedPhoneNumber,
                            editedAddress,
                            editedEmail,
                            editedGroupName
                    );
                    assert initialData != null; // these lines update the data in the tableView, so we don't have to refresh the table
                    initialData.setName(editedName);
                    initialData.setPhoneNumber(editedPhoneNumber);
                    initialData.setAddress(editedAddress);
                    initialData.setEmail(editedEmail);
                    initialData.setGroupName(editedGroupName);



                    Stage stage = (Stage) editBtn.getScene().getWindow();
                    stage.close(); // closes the GUI after the operation is finished.
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

}
