package project.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import project.Delivery;
import project.Order;

public class InProgressController {

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
    void save(ActionEvent event) {
    }

    @FXML
    void initialize() {
        table();
    }

    void table(){
        ObservableList<Delivery> oblist = Delivery.data();
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

    @FXML
    int getSelected(){
        int index = -1;

        index = tableOrders.getSelectionModel().getSelectedIndex();


        return index;
    }

    @FXML
    void complete() {
        Connection conn = project.MySQL.DBConnect();

        int index = getSelected();

        int recentQuantity = quantity_col.getCellData(index);
        int order_id = order_id_col.getCellData(index);
        int product_id = product_id_col.getCellData(index);
        int employee_id=authController.id;

        System.out.println(order_id);

        int delivery=1;

        double price = 0;

        int quantity = quantity_col.getCellData(getSelected());

        if (quantity > recentQuantity) {
            System.out.println("Incorrect value");
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT price FROM products WHERE product_id = "+product_id);
            while (rs.next()) {
                price = rs.getDouble(1);
                System.out.println(price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        double total_price = price*quantity;


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(formatter.format(date));


        String delivery_time = formatter.format(date);
        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO `deliveries`(`product_id`, `order_id`, `employee_id`, `delivery_date`) VALUES (?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);
            ps.setInt(1, product_id);
            ps.setInt(2, order_id);
            ps.setInt(3,employee_id);
            ps.setString(4, delivery_time);
            ps.execute();

            String sql = "INSERT INTO `sales`(`product_id`, `order_id`, `employee_id`, `isDelivered`, `quantity`, `total_price`, `sale_date`) VALUES (?,?,?,?,?,?,?)";
            assert conn != null;

//            INSERT INTO `sales`(`sale_id`, `product_id`, `order_id`, `customer_name`, `employee_id`, `delivery`, `quantity`, `total_price`, `sale_time`) VALUES

            ps = conn.prepareStatement(sql);
            ps.setInt(1,product_id);
            ps.setInt(2,order_id);
            ps.setInt(3,employee_id);
            ps.setInt(4,delivery);
            ps.setInt(5,quantity);
            ps.setDouble(6,total_price);
            ps.setString(7, delivery_time);
            ps.execute();

            String sql_delete = ("DELETE FROM `deliveryinprogress` WHERE order_id = "+order_id);
            ps=conn.prepareStatement(sql_delete);
            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The order was successfully completed");
            alert.showAndWait();
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

        table();
    }

}
