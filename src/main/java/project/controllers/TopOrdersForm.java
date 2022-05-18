package project.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import project.Appliance;

public class TopOrdersForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView avatarAppliance;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptiontextArea;

    @FXML
    private TextField discountField;

    @FXML
    private JFXButton exportButton1;

    @FXML
    private TextField factoryField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField nameField;

    @FXML
    private JFXButton photoButton;

    @FXML
    private TextField priceField;

    @FXML
    private Pane productsPane;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField searchField;

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
    private TableColumn<Appliance, Integer> quantity_col;

    @FXML
    private TextField yearField;

    @FXML
    private ComboBox<Integer> monthFieldCombo;
    @FXML
    private ComboBox<Integer> yearFieldCombo;
    @FXML
    private DatePicker dateField;
    @FXML
    private JFXButton submitButton;


    @FXML
    void ExportToExcel(ActionEvent event) {

    }

    @FXML
    void UpdateImage(ActionEvent event) {

    }

    @FXML
    void getSelected(MouseEvent event) {

    }

    void fillComboBoxes(){
        ObservableList<Integer> months = FXCollections.observableArrayList();
        for (int i = 0; i <= 12; i++){
            months.add(i);
        }
        monthFieldCombo.setItems(months);
        ObservableList<Integer> years = FXCollections.observableArrayList();
        years.add(0);
        for (int i = 2017; i<=2022; i++){
            years.add(i);
        }
        yearFieldCombo.setItems(years);
    }

    @FXML
    void initialize() {
        table();
        fillComboBoxes();
    }

    void table(){
        ObservableList<Appliance> oblist = Appliance.getDataForReport("orders", "order_date");

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
        factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        tableProducts.setItems(oblist);
    }

    @FXML
    void getDate(){
        tableProducts.getItems().clear();
        if(dateField != null){
            LocalDate date = dateField.getValue();
            String strDate = String.valueOf(date);

            ObservableList<Appliance> oblist = Appliance.getDataForReport("sales", "sale_date", strDate);

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
            factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
            category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
            quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            tableProducts.setItems(oblist);
        }

        if(monthFieldCombo.getValue() != null && yearFieldCombo.getValue() != null && dateField.getValue() == null){
            int month = monthFieldCombo.getValue();
            int year = yearFieldCombo.getValue();

            ObservableList<Appliance> oblist = Appliance.getDataForReport("sales", month, year);

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
            factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
            category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
            quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            tableProducts.setItems(oblist);
        }

        if((monthFieldCombo.getValue()==null || monthFieldCombo.getValue()==0) && dateField.getValue() == null){
            int year = yearFieldCombo.getValue();

            ObservableList<Appliance> oblist = Appliance.getDataForReport("sales", year);

            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            model_col.setCellValueFactory(new PropertyValueFactory<>("model"));
            factory_col.setCellValueFactory(new PropertyValueFactory<>("factory"));
            category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
            quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            tableProducts.setItems(oblist);
        }
    }
}