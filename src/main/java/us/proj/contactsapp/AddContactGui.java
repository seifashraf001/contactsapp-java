package us.proj.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddContactGui extends Application {

    public Scene addMenuScene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader AddMenuLoader = new FXMLLoader(this.getClass().getResource("add-view.fxml"));
        this.addMenuScene = new Scene(AddMenuLoader.load(), 600, 700);
        stage.setScene(addMenuScene);
        stage.setResizable(false);
        stage.getIcons().add(ContactsApp.getLogo());
        stage.setTitle("Contacts - Add new Contact");
        stage.show();
    }
}