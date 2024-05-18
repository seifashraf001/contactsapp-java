package us.proj.contactsapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import us.proj.contactsapp.*;
import us.proj.contactsapp.db.DatabaseConnection;
import us.proj.contactsapp.objects.Contact;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    AddContactGui addContactGui = new AddContactGui();
    AboutGui aboutGui = new AboutGui();

    // Table Stuff
    @FXML
    private TableView<Contact> tableView;

    @FXML
    private TableColumn<Contact, String> contactID;

    @FXML
    private TableColumn<Contact, String> name;

    @FXML
    private TableColumn<Contact, String> phoneNumber;

    @FXML
    private TableColumn<Contact, String> address;

    @FXML
    private TableColumn<Contact, String> email;

    @FXML
    private TableColumn<Contact, String> groupName;
    // Buttons

    @FXML
    public Button refreshBtn;

    @FXML
    private Button aboutBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    // Search
    @FXML
    TextField searchBox;

    private String query = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Contact selectedContact;

    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    private void loadData() {
        try {
            contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refreshTable() {
        try {
            contactList.clear();
            query = "SELECT * FROM CONTACTS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                contactList.add(new Contact(
                        resultSet.getInt("contactID"),
                        resultSet.getString("name"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("groupName")));
            }
            tableView.setItems(contactList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DatabaseConnection.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadData();
        refreshTable();

        // Load the initial data into the table
        tableView.setItems(contactList);
    }

    @FXML
    public void onAddClick(ActionEvent event) throws Exception {
        if (event.getSource().equals(addBtn)) {
            Stage addMenuStage = new Stage();
            addContactGui.start(addMenuStage);
        }
    }

    @FXML
    public void onAboutClick(ActionEvent event) throws Exception {
        if (event.getSource().equals(aboutBtn)) {
            Stage aboutMenuStage = new Stage();
            aboutGui.start(aboutMenuStage);
        }
    }

    @FXML
    public void onRefreshClick(ActionEvent event) throws Exception{
        refreshTable();
        System.out.println("table is refreshed!"); // debugging.
    }

    private void deleteSelectedContact() {
        try {
            selectedContact = tableView.getSelectionModel().getSelectedItem();
            int contactID = selectedContact.getContactID();

            // Delete the selected contact from the database
            String deleteQuery = "DELETE FROM CONTACTS WHERE contactID = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, contactID);
            preparedStatement.executeUpdate();

            // Refresh the table to reflect the changes
            refreshTable();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed (show an error message, log, etc.)
        }
    }

    @FXML
    public void onDeleteClick(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) { // checks if a row is selected for deletion or not.
            // Display a confirmation dialog before deleting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this contact?",
                    ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // User confirmed, proceed with deletion
                deleteSelectedContact();
            }
        } else {
            // No row selected, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a contact to delete.", ButtonType.OK);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(ContactsApp.getLogo());
            alert.showAndWait();
        }
    }

    @FXML
    private void onEditClick(ActionEvent event) throws Exception {
        Contact selectedData = tableView.getSelectionModel().getSelectedItem();

        if (selectedData != null) {
            if (event.getSource().equals(editBtn)) {

                FXMLLoader loader = new FXMLLoader(EditContactGui.class.getResource("edit-view.fxml")); // this part of the code is to show the selected data from the row in the Edit GUI
                loader.setControllerFactory(c -> new EditMenuController(selectedData)); //
                Parent view = loader.load(); //
                EditMenuController editMenuController = (EditMenuController) loader.getController(); //
                editMenuController.initialize(); //
                Stage stage = new Stage(); //
                stage.setScene(new Scene(view)); //
                stage.show(); //
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a contact from the table to edit.", ButtonType.OK);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(ContactsApp.getLogo());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearch() {
        // Get the text from the search box
        String searchText = searchBox.getText().toLowerCase();

        // Filter the contactList based on the search text
        ObservableList<Contact> filteredList = contactList.filtered(contact ->
                contact.getName().toLowerCase().contains(searchText) ||
                        contact.getPhoneNumber().toLowerCase().contains(searchText) ||
                        contact.getAddress().toLowerCase().contains(searchText) ||
                        contact.getEmail().toLowerCase().contains(searchText) ||
                        contact.getGroupName().toLowerCase().contains(searchText)
        );

        // Update the table with the filtered list
        tableView.setItems(filteredList);
    }
}