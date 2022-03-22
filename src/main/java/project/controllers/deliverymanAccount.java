package project.controllers;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Main;

import java.io.IOException;

public class deliverymanAccount {

    public AnchorPane context;


    @FXML
    void initialize() throws IOException {
        btnMain();
        new FadeIn(context).play();
    }


    private void setPane(String location) throws IOException {
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(Main.class.
                getResource("fxml/" + location + ".fxml")));
    }

    @FXML
    public void btnMain() throws IOException {
        setPane("MainDeliverymanForm");
        new FadeIn(context).play();
    }

    @FXML
    public void btnOrders() throws IOException {
        setPane("OrdersForm");
        new FadeIn(context).play();
    }

    @FXML
    public void btnMyDeliveries() throws IOException {
        setPane("MyDeliveries");
        new FadeIn(context).play();
    }

    public void btnInprogress()throws IOException {
        setPane("InprogressForm");
        new FadeIn(context).play();
    }
    public void btnSales()throws IOException {
        setPane("SalesForm");
        new FadeIn(context).play();
    }

    @FXML
    public void btnSignOut() {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/auth.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("worker");
        stage.setScene(scene);
        stage.show();
    }
}

