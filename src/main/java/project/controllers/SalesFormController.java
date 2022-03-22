package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import project.Appliance;
import project.MySQL;
import project.Sale;

public class SalesFormController {

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
    private Label totalLabel;
    @FXML
    private Label totalquantityLabel;

    @FXML
    private TableView<Sale> tableSales;
    @FXML
    private TableColumn<Sale, Integer> id_col;
    @FXML
    private TableColumn<Sale, String> product_col;
    @FXML
    private TableColumn<Sale, Integer> quantity_col;
    @FXML
    private TableColumn<Sale, String> client_col;
    @FXML
    private TableColumn<Sale, Integer> delivery_col;
    @FXML
    private TableColumn<Sale, Integer> employee_col;
    @FXML
    private TableColumn<Sale, Double> totalprice_col;
    @FXML
    private TableColumn<Sale, String> saletime_col;

    @FXML
    void initialize() {
        totalLabel.setText("TOTAL EARNINGS: "+Sale.getTotalEarnings()+"       ");
        totalLabel.setVisible(true);
        totalquantityLabel.setText("TOTAL QUANTITY OF SALES: "+Sale.getTotalQuantity());
        totalquantityLabel.setVisible(true);
        table();
        search();
    }
    void table(){
        ObservableList<Sale> oblist = Sale.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("sale_id"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        client_col.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        employee_col.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        delivery_col.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalprice_col.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        saletime_col.setCellValueFactory(new PropertyValueFactory<>("sale_time"));

        tableSales.setItems(oblist);
    }

    void search(){
        ObservableList<Sale> oblist = Sale.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<>("sale_id"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        client_col.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        employee_col.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        delivery_col.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalprice_col.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        saletime_col.setCellValueFactory(new PropertyValueFactory<>("sale_time"));

        tableSales.setItems(oblist);


        FilteredList<Sale> filteredData = new FilteredList<>(oblist, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sale -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (sale.getProduct().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (sale.getStringValueOfID().contains(lowerCaseFilter)) {
                    return true;
                }else if (sale.getStringValurOfEmployeeID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (sale.getSale_time().contains(lowerCaseFilter)){
                    return true;
                }else if(sale.getStringValueOfTotalPrice().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Sale> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableSales.comparatorProperty());
        tableSales.setItems(sortedData);
    }

    @FXML
    void ExportToExcel(){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sales.*,(SELECT CONCAT(product.name, ' ', product.model) FROM product WHERE product.product_id = sales.product_id) AS product FROM sales");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sales");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("PRODUCT");
            header.createCell(2).setCellValue("CLIENT NAME");
            header.createCell(3).setCellValue("EMPLOYEE ID");
            header.createCell(4).setCellValue("DELIVERY");
            header.createCell(5).setCellValue("QUANTITY");
            header.createCell(6).setCellValue("TOTAL PRICE");
            header.createCell(7).setCellValue("SALE TIME");


            int rowId = 1;
            while (rs.next()){
                Row row = sheet.createRow(rowId);
                row.createCell(0).setCellValue(rs.getInt("sale_id"));
                row.createCell(1).setCellValue(rs.getString("product"));
                row.createCell(2).setCellValue(rs.getString("customer_name"));
                row.createCell(3).setCellValue(rs.getInt("employee_id"));
                row.createCell(4).setCellValue(rs.getInt("delivery"));
                row.createCell(5).setCellValue(rs.getInt("quantity"));
                row.createCell(6).setCellValue(rs.getDouble("total_price"));
                row.createCell(7).setCellValue(rs.getString("sale_time"));
                rowId++;


            }
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog(exportButton1.getScene().getWindow());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getAbsolutePath()));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Success");




        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
