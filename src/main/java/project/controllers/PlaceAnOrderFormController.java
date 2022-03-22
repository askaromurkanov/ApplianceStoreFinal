package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import project.Appliance;

public class PlaceAnOrderFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private ImageView avatarAppliance;

    @FXML
    private TextField bonuscardField;

    @FXML
    private JFXButton cleanButton;

    @FXML
    private JFXButton completeOrderButton;

    @FXML
    private TextField discountField;



    @FXML
    private TextField mailField;

    @FXML
    private JFXButton minusButton;

    @FXML
    private TextField modelField;

    @FXML
    private TextField nameCustomerField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phonenumberField;

    @FXML
    private JFXButton plusButton;

    @FXML
    private TextField priceField;

    @FXML
    private Pane productsPane;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField quantityorderField;

    @FXML
    private TextField searchField;


    @FXML
    private TableView<Appliance> tableProducts;
    @FXML
    private TableColumn<Appliance, Double> discount_col;
    @FXML
    private TableColumn<Appliance, Integer> id_col;
    @FXML
    private TableColumn<Appliance, Integer> quantity_col;
    @FXML
    private TableColumn<Appliance, Double> price_col;
    @FXML
    private TableColumn<Appliance, String> name_col;
    @FXML
    private TableColumn<Appliance,String> model_col;


    @FXML
    void clean(ActionEvent event) {

    }

    int currentQuantity = 0;

    @FXML
    void initialize() {
        table();
        search();


        plusButton.setOnAction(event -> {
            quantityorderField.setText(String.valueOf(currentQuantity+1));
        });
        minusButton.setOnAction(event -> {
            quantityorderField.setText(String.valueOf(currentQuantity+1));
        });
    }


    void table(){
        ObservableList<Appliance> oblist = Appliance.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
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

        File file = new File(Appliance.getImgPath(id));
        Image image = new Image(file.toURI().toString());
        avatarAppliance.setImage(image);

        nameField.setText(name_col.getCellData(index));
        modelField.setText(model_col.getCellData(index));
        quantityField.setText(quantity_col.getCellData(index).toString());
        priceField.setText(price_col.getCellData(index).toString());
        discountField.setText(discount_col.getCellData(index).toString());

        return index;
    }

    int recentQuality(){
        int index = -1;

        index = tableProducts.getSelectionModel().getSelectedIndex();

        quantityField.setText(String.valueOf(quantity_col.getCellData(index)));
        int recentQuantity = quantity_col.getCellData(index);

        return recentQuantity;
    }
    int quantity(){
        int quantity = Integer.parseInt(quantityorderField.getText());
        return quantity;
    }

    @FXML
    void placeorder(){

        int quantity = quantity();
        int recentQuantity = recentQuality();

        int index = getSelected();

        String fullname = nameCustomerField.getText();
        int phonenumber = Integer.parseInt(phonenumberField.getText());
        String mail = mailField.getText();
        String address = addressField.getText();



        int product_id=id_col.getCellData(index);

        if (quantity>recentQuantity){
            System.out.println("Incorrect value. More than expected");
        }



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));


        String order_time = formatter.format(date);
        Connection conn = project.MySQL.DBConnect();
        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO `orders`(`product_id`, `customer_name`, `phonenumber`, `mail`, `address`, `quantity`, `order_time`) VALUES (?,?,?,?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);
            ps.setInt(1,product_id);
            ps.setString(2,fullname);
            ps.setInt(3,phonenumber);
            ps.setString(4,mail);
            ps.setString(5,address);
            ps.setInt(6,quantity);
            ps.setString(7,order_time);
            ps.execute();


            int newQuantity = recentQuantity - quantity;

            String sql_update = ("UPDATE product SET quantity = ? WHERE product_id = "+product_id);
            ps=conn.prepareStatement(sql_update);
            ps.setInt(1, newQuantity);
            ps.execute();





            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The order has been successfully placed");
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



