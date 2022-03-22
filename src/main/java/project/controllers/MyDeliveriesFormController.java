package project.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import project.Delivery;
import project.MySQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MyDeliveriesFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane addProductsPane;

    @FXML
    private JFXButton deliverButton;

    @FXML
    private JFXButton exportButton1;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<Delivery> tableOrders;
    @FXML
    private TableColumn<Delivery, Integer> id_col;
    @FXML
    private TableColumn<Delivery, Integer> order_id_col;
    @FXML
    private TableColumn<Delivery, String> order_time_col;
    @FXML
    private TableColumn<Delivery, String> product_col;
    @FXML
    private TableColumn<Delivery, Integer> product_id_col;
    @FXML
    private TableColumn<Delivery, String> addressCol;
    @FXML
    private TableColumn<Delivery, Integer> phonenumber_col;
    @FXML
    private TableColumn<Delivery, String> customer_name;
    @FXML
    private TableColumn<Delivery, Integer> quantity_col;

    @FXML
    private Label totalLabel;

    @FXML
    void getSelected(MouseEvent event) {

    }

    @FXML
    void save(ActionEvent event) {
    }

    @FXML
    void initialize() {
        table();
    }

    void table(){
        ObservableList<Delivery> oblist = dataMyDeliveries();
        id_col.setCellValueFactory(new PropertyValueFactory<>("deliver_id"));
        order_id_col.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        product_id_col.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        phonenumber_col.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        order_time_col.setCellValueFactory(new PropertyValueFactory<>("order_time"));

        tableOrders.setItems(oblist);
    }

    public static ObservableList<Delivery> dataMyDeliveries(){
        ObservableList<Delivery> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT deliveries.*,(SELECT CONCAT(product.name, ' ', product.model) FROM product WHERE product.product_id = deliveries.product_id) AS product, (SELECT orders.mail FROM orders WHERE deliveries.order_id = orders.order_id) AS mail, (SELECT orders.customer_name FROM orders WHERE deliveries.order_id = orders.order_id) AS customer_name,(SELECT orders.address FROM orders WHERE deliveries.order_id = orders.order_id) AS address, (SELECT orders.phonenumber FROM orders WHERE deliveries.order_id = orders.order_id) AS phonenumber FROM deliveries WHERE employee_id = "+authController.id);
            while (rs.next()) {
                oblist.add(new Delivery(rs.getInt("delivery_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getInt("phonenumber"),
                        rs.getInt("quantity"),
                        rs.getString("delivery_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return oblist;
    }


}
