package us.proj.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AboutGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader aboutViewLoader = new FXMLLoader(this.getClass().getResource("about-view.fxml"));
        Scene scene = new Scene(aboutViewLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Contacts App - About");
        stage.getIcons().add(ContactsApp.getLogo());
        stage.show();
    }
}
