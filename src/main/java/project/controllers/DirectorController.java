package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Main;

public class DirectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public JFXButton addProductButton;

    @FXML
    private JFXButton customersButton;

    @FXML
    private AnchorPane holderPane;

    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton newEmployeeButton;

    @FXML
    private JFXButton productsButton;

    @FXML
    private JFXButton salesButton;

    @FXML
    private JFXButton signOutButton;

    @FXML
    private JFXButton staffButton;


    StackPane home;

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

    public void btnEmployees() throws IOException {
        setPane("employees");
        new FadeIn(context).play();

    }


    public void btnNewEmployee() throws IOException {
        setPane("NewEmployeeForm");
        new FadeIn(context).play();
    }

    public void btnTopEmployees() throws IOException {
        setPane("top-employees");
        new FadeIn(context).play();

    }


    public void btnAddProducts() throws IOException {
        setPane("AddProductsForm");
        new FadeIn(context).play();
    }

    public void btnProducts() throws IOException {
        setPane("ProductsForm");
        new FadeIn(context).play();
    }

    public void btnSales() throws IOException {
        setPane("SalesForm");
        new FadeIn(context).play();
    }

    public void btnOrders() throws IOException {
        setPane("OrdersForm");
        new FadeIn(context).play();
    }

    public void btnTopProductOrders() throws IOException {
        setPane("report_orders");
        new FadeIn(context).play();
    }
    public void btnTopProductSales() throws IOException {
        setPane("report_sales");
        new FadeIn(context).play();
    }
    public void btnMain() throws IOException {
        setPane("MainDirectorForm");
        new FadeIn(context).play();
    }
    public void btnSignOut() throws IOException, Exception{
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


