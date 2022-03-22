package project.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import project.Customer;
import project.Employee;

public class CustomersFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;


    @FXML
    private ImageView avatarImg;


    @FXML
    private Pane customersPane;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton exportButton1;



    @FXML
    private TextField nameField;



    @FXML
    private JFXButton photoButton;


    @FXML
    private TextField mailField;


    @FXML
    private TextField purchaseField;
    @FXML
    private TextField searchField;

    @FXML
    private TextField surnameField;
    @FXML
    private TextField phonenumberField;





    @FXML
    private JFXButton updateButton;


    @FXML
    private TableView<Customer> tableCustomers;
    @FXML
    private TableColumn<Customer, String> address_col;
    @FXML
    private TableColumn<Customer, Integer> id_col;
    @FXML
    private TableColumn<Customer, String> mail_col;
    @FXML
    private TableColumn<Customer, String> name_col;
    @FXML
    private TableColumn<Customer, String> surname_col;
    @FXML
    private TableColumn<Customer, String> password_col;
    @FXML
    private TableColumn<Customer, Integer> phonenumber_col;
    @FXML
    private TableColumn<Customer, Integer> purchases_col;
    @FXML
    private TableColumn<Customer, String> username_col;

    @FXML
    void initialize() {
        table();
    }

    void table(){
        ObservableList<Customer> oblist = Customer.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname_col.setCellValueFactory(new PropertyValueFactory<>("surname"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        mail_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phonenumber_col.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        purchases_col.setCellValueFactory(new PropertyValueFactory<>("purchases"));
        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        password_col.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableCustomers.setItems(oblist);
    }

    void search(){
        ObservableList<Customer> oblist = Customer.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname_col.setCellValueFactory(new PropertyValueFactory<>("surname"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        mail_col.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phonenumber_col.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        purchases_col.setCellValueFactory(new PropertyValueFactory<>("purchases"));
        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        password_col.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableCustomers.setItems(oblist);

        FilteredList<Customer> filteredData = new FilteredList<>(oblist, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getStringValueOfID().contains(lowerCaseFilter)) {
                    return true;
                }else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (customer.getMail().contains(lowerCaseFilter)){
                    return true;
                }else if(customer.getSurname().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(customer.getStringValurOfPhonenumber().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableCustomers.comparatorProperty());
        tableCustomers.setItems(sortedData);
    }

    @FXML
    public void delete(){
        int index = getSelected();
        int id = id_col.getCellData(index);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm the employee's dismissal");
        alert.setHeaderText("Are you sure you want to delete a customer");
        alert.setContentText("If you click OK, all the data of this customer will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Employee.deleteCustomer(id);
            table();
            search();
            System.out.println("OKKKK");
        }
    }


    @FXML
    int getSelected(){
        int index = -1;

        index = tableCustomers.getSelectionModel().getSelectedIndex();

        nameField.setText(name_col.getCellData(index));
        surnameField.setText(surname_col.getCellData(index));
        addressField.setText(address_col.getCellData(index));
        mailField.setText(mail_col.getCellData(index));
        purchaseField.setText(purchases_col.getCellData(index).toString());
        phonenumberField.setText(phonenumber_col.getCellData(index).toString());

        return index;
    }

}

