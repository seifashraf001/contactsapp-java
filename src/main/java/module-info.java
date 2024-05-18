module us.proj.contactsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens us.proj.contactsapp to javafx.fxml;
    opens us.proj.contactsapp.controllers to javafx.fxml; // Open the controllers package

    exports us.proj.contactsapp.controllers;
    exports us.proj.contactsapp;
    exports us.proj.contactsapp.objects;
    opens us.proj.contactsapp.objects to javafx.fxml;

    // other requires and exports as needed
}
