package project.controllers;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Main;

public class workerAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton customersButton;

    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton products;

    @FXML
    private JFXButton salesButton;

    @FXML
    private JFXButton sell;

    @FXML
    private JFXButton signOutButton;

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
    public void btnProducts() throws IOException {
        setPane("ProductsForm");
        new FadeIn(context).play();
    }

    @FXML
    public void btnSales() throws IOException {
        setPane("MySalesForm");
        new FadeIn(context).play();
    }


    public void btnSell()throws IOException {
        setPane("SellForm");
        new FadeIn(context).play();
    }
    public void btnPlaceOrder()throws IOException{
        setPane("placeAnOrderForm");
        new FadeIn(context).play();
    }

    public void btnMain()throws IOException{
        setPane("MainEmployeeForm");
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


