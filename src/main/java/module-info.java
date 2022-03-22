module project{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi.ooxml;
    requires poi;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.swing;
    requires AnimateFX;


    opens project.controllers to javafx.fxml;
    exports project.controllers;

    opens project to javafx.fxml;
    exports project;

}