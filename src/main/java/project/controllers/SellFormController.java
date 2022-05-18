package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import project.Appliance;

public class SellFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton exportButton1;

    @FXML
    private Pane productsPane;

    @FXML
    private TextField searchField;

    @FXML
    private JFXButton sellButton;

    @FXML
    private Label totalLabel;


    @FXML
    void update(ActionEvent event) {

    }
    @FXML
    private TextField quantityField;


    @FXML
    private TableView<Appliance> tableProducts;
    @FXML
    private TableColumn<Appliance, Integer> id_col;
    @FXML
    private TableColumn<Appliance, String> name_col;
    @FXML
    private TableColumn<Appliance, String> model_col;
    @FXML
    private TableColumn<Appliance, String> factory_col;
    @FXML
    private TableColumn<Appliance, String> category_col;
    @FXML
    private TableColumn<Appliance, Integer> year_col;
    @FXML
    private TableColumn<Appliance, Integer> quantity_col;
    @FXML
    private TableColumn<Appliance, Double> price_col;
    @FXML
    private TableColumn<Appliance, Double> discount_col;

    @FXML
    void initialize() {
        table();
        search();

    }

    int getID(int id){
        return id;
    }

    void table(){
        ObservableList<Appliance> oblist = Appliance.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        discount_col.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableProducts.setItems(oblist);
    }
    void search(){
        ObservableList<Appliance> oblist = Appliance.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        discount_col.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableProducts.setItems(oblist);


        FilteredList<Appliance> filteredData = new FilteredList<>(oblist, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(appliance -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (appliance.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (appliance.getStringValueOfID().contains(lowerCaseFilter)) {
                    return true;
                }else if (appliance.getModel().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (appliance.getCategory().contains(lowerCaseFilter)){
                    return true;
                }else if(appliance.getFactory().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(appliance.getStringValueOfID().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(appliance.getStringValueOfYear().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(appliance.getStringValueOfQuantity().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Appliance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableProducts.comparatorProperty());
        tableProducts.setItems(sortedData);
    }
    @FXML
    int getSelected(){
        int index = -1;

        index = tableProducts.getSelectionModel().getSelectedIndex();

        int id = id_col.getCellData(index);

        quantityField.setText(String.valueOf(quantity_col.getCellData(index)));

        return index;
    }

    int recentQuantity(){
        int index = -1;

        index = tableProducts.getSelectionModel().getSelectedIndex();

        quantityField.setText(String.valueOf(quantity_col.getCellData(index)));
        int recentQuantity = quantity_col.getCellData(index);

        return recentQuantity;
    }
    int quantity(){
        int quantity = Integer.parseInt(quantityField.getText());
        return quantity;
    }

    @FXML
    void sell(){
        int quantity = quantity();
        int recentQuantity = recentQuantity();
        int index = getSelected();

        int product_id=id_col.getCellData(index);
        int employee_id=authController.id;
        int delivery=0;

        if (quantity>recentQuantity){
            System.out.println("Incorrect value");
        }

        double total_price = price_col.getCellData(index)*quantity;


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(formatter.format(date));
        String sale_time = formatter.format(date);

        Connection conn = project.MySQL.DBConnect();
        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO sales (`product_id`, `employee_id`, `isDelivered`, `quantity`, `total_price`, `sale_date`, `order_id`) VALUES (?,?,?,?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);
            ps.setInt(1,product_id);
            ps.setInt(2,employee_id);
            ps.setInt(3,delivery);
            ps.setInt(4,quantity);
            ps.setDouble(5,total_price);
            ps.setString(6, sale_time);
            ps.setString(7, null);
            ps.execute();

            int newQuantity = recentQuantity - quantity;

            String sql_update = ("UPDATE products SET quantity = ? WHERE product_id = "+product_id);
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
