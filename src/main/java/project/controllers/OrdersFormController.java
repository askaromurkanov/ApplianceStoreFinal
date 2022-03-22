package project.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import project.Order;
import project.Sale;

public class OrdersFormController {

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
    private TextField searchField;

    @FXML
    private TableView<?> tableProducts;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<Order> tableOrders;
    @FXML
    private TableColumn<Order, Integer> id_col;
    @FXML
    private TableColumn<Order, String> order_time_col;
    @FXML
    private TableColumn<Order, Integer> phonenumber_col;
    @FXML
    private TableColumn<Order, String> product_col;
    @FXML
    private TableColumn<Order, Integer> product_id_col;
    @FXML
    private TableColumn<Order, String> addressCol;
    @FXML
    private TableColumn<Order, Integer> mail_col;
    @FXML
    private TableColumn<Order, String> customer_name;
    @FXML
    private TableColumn<Order, Integer> quantity_col;


    @FXML
    void initialize() {
        table();
        search();
//        recentQuality();
//        quantity();
    }

    void table(){
        ObservableList<Order> oblist = Order.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        product_id_col.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        mail_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phonenumber_col.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        order_time_col.setCellValueFactory(new PropertyValueFactory<>("order_time"));

        tableOrders.setItems(oblist);
    }
    void search(){
        ObservableList<Order> oblist = Order.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        product_id_col.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        mail_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phonenumber_col.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        order_time_col.setCellValueFactory(new PropertyValueFactory<>("order_time"));

        tableOrders.setItems(oblist);

        FilteredList<Order> filteredData = new FilteredList<>(oblist, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (order.getProduct().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getStringValueOfID().contains(lowerCaseFilter)) {
                    return true;
                }else if (order.getMail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (order.getOrder_time().contains(lowerCaseFilter)){
                    return true;
                }else if(order.getAddress().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if(order.getCustomer_name().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(order.getStringValurOfPhonenumberID().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableOrders.comparatorProperty());
        tableOrders.setItems(sortedData);
    }

    @FXML
    int get(){
        int index = -1;

        index = tableOrders.getSelectionModel().getSelectedIndex();

        quantityField.setText(quantity_col.getCellData(index).toString());
        int recentQuantity = quantity_col.getCellData(index);

        return index;
    }

    int recentQuality(){
        int index = -1;

        index = tableOrders.getSelectionModel().getSelectedIndex();

        quantityField.setText(String.valueOf(quantity_col.getCellData(index)));
        int recentQuantity = quantity_col.getCellData(index);

        return recentQuantity;
    }
    int quantity(){
        int quantity = Integer.parseInt(quantityField.getText());
        return quantity;
    }


    @FXML
    void deliver(){

        Connection conn = project.MySQL.DBConnect();
        int quantity = quantity();
        int recentQuantity = recentQuality();
        int index = get();
        int order_id = id_col.getCellData(index);
        int product_id = product_id_col.getCellData(index);
        String mail = String.valueOf(mail_col.getCellData(index));
        String address = addressCol.getCellData(index);
        String order_time = order_time_col.getCellData(index);

//        double price = 0;


        if (quantity>recentQuantity){
            System.out.println("Incorrect value");
        }

//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT price FROM product WHERE product_id = "+product_id);
//            while (rs.next()) {
//                price = rs.getDouble(1);
//                System.out.println(price);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }



//        double total_price = price*quantity;

        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO `deliveryinprogress`(`order_id`, `product_id`, `address`, `quantity`, `order_time`) VALUES (?,?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);
            ps.setInt(1,order_id);
            ps.setInt(2,product_id);
            ps.setString(3,address);
            ps.setInt(4,quantity);
            ps.setString(5,order_time);
            ps.execute();

            int newQuantity = recentQuantity - quantity;

            String sql_update = ("UPDATE orders SET quantity = ? WHERE product_id = "+product_id);
            ps=conn.prepareStatement(sql_update);
            ps.setInt(1, newQuantity);
            ps.execute();



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The product was successfully sold");
            alert.showAndWait();
        }catch (SQLException e) {
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


