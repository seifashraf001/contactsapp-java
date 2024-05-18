package us.proj.contactsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ContactsApp extends Application {
    public static Image getLogo() {
        return new Image(ContactsApp.class.getResource("/us/proj/contactsapp/logo.png").toExternalForm());
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homeViewRoot = new FXMLLoader(this.getClass().getResource("home-view.fxml"));
        Scene homeScene = new Scene(homeViewRoot.load(), 1200, 808);

        stage.getIcons().add(getLogo());
        stage.setResizable(false);
        stage.setTitle("Contacts App");
        stage.setScene(homeScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}