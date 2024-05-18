package us.proj.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditContactGui extends Application {
    public Scene addMenuScene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader AddMenuLoader = new FXMLLoader(this.getClass().getResource("edit-view.fxml"));
        this.addMenuScene = new Scene(AddMenuLoader.load(), 600, 700);
        stage.setScene(addMenuScene);
        stage.setResizable(false);
        stage.getIcons().add(ContactsApp.getLogo());
        stage.setTitle("Contacts - Edit a Contact");
        stage.show();
    }
}
