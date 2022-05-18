package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import project.Appliance;
import project.Employee;
import project.MySQL;

import javax.imageio.ImageIO;

public class ProductsFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView avatarAppliance;

    @FXML
    private TextField categoryField;

    @FXML
    private JFXButton deleteButton;

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
    private JFXButton updateButton;

    @FXML
    private TextField yearField;

    @FXML
    private TextArea descriptiontextArea;

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

        File file = new File(Appliance.getImgPath(id));
        Image image = new Image(file.toURI().toString());
        avatarAppliance.setImage(image);

        nameField.setText(name_col.getCellData(index));
        modelField.setText(model_col.getCellData(index));
        factoryField.setText(factory_col.getCellData(index));
        categoryField.setText(category_col.getCellData(index));
        yearField.setText(year_col.getCellData(index).toString());
        quantityField.setText(quantity_col.getCellData(index).toString());
        priceField.setText(price_col.getCellData(index).toString());
        discountField.setText(discount_col.getCellData(index).toString());
        descriptiontextArea.setText(Appliance.getDescription(id));

        return index;
    }

    @FXML
    void delete(){
        int id = id_col.getCellData(getSelected());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm the removal of the product");
        alert.setHeaderText("Are you sure you want to delete a product");
        alert.setContentText("If you click OK, all the data of this product will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Appliance.deleteAppliance(id);
            table();
            search();
            System.out.println("OKKKK");
        }
    }

    @FXML
    void update(){
        String name = nameField.getText();
        String model = modelField.getText();
        String factory = factoryField.getText();
        String category = categoryField.getText();
        int year = Integer.parseInt(yearField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        double discount = Double.parseDouble(discountField.getText());
        String description = descriptiontextArea.getText();

        int ID = id_col.getCellData(getSelected());

        Connection conn = MySQL.DBConnect();
        PreparedStatement ps = null;
        try {
            String sql = ("UPDATE product SET name = ?, model = ?, factory = ?, category = ?, year = ?, quantity = ?, discount = ?, description = ? WHERE product_id = "+ID);
            ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, model);
            ps.setString(3, factory);
            ps.setString(4, category);
            ps.setInt(5,year);
            ps.setInt(6, quantity);
            ps.setDouble(7, discount);
            ps.setString(8, description);

            ps.execute();
            clean();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The record in the table has been successfully updated");
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
        search();
        clean();
        System.out.println("OKKKK");
    }

    @FXML
    void UpdateImage(){
        int index = getSelected();
        int id = id_col.getCellData(index);

        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(tableProducts.getScene().getWindow());
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            String img_path = file.getAbsolutePath();
            System.out.println(img_path);
            avatarAppliance.setImage(image);
            Appliance.UpdateProductImage(id,img_path);
//            workbook.write(fileOutputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void clean(){
        nameField.setText(null);
        modelField.setText(null);
        factoryField.setText(null);
        categoryField.setText(null);
        yearField.setText(null);
        quantityField.setText(null);
        priceField.setText(null);
        discountField.setText(null);
        descriptiontextArea.setText(null);
    }

    @FXML
    void ExportToExcel(){
        try {
            Connection conn = MySQL.DBConnect();
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Product");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("NAME");
            header.createCell(2).setCellValue("MODEL");
            header.createCell(3).setCellValue("FACTORY");
            header.createCell(4).setCellValue("CATEGORY");
            header.createCell(5).setCellValue("YEAR");
            header.createCell(6).setCellValue("QUANTITY");
            header.createCell(7).setCellValue("PRICE");
            header.createCell(8).setCellValue("DISCOUNT");
            header.createCell(9).setCellValue("DESCRIPTION");

            int rowId = 1;
            while (rs.next()){
                Row row = sheet.createRow(rowId);
                row.createCell(0).setCellValue(rs.getInt("product_id"));
                row.createCell(1).setCellValue(rs.getString("name"));
                row.createCell(2).setCellValue(rs.getString("model"));
                row.createCell(3).setCellValue(rs.getString("factory"));
                row.createCell(4).setCellValue(rs.getString("category"));
                row.createCell(5).setCellValue(rs.getInt("year"));
                row.createCell(6).setCellValue(rs.getInt("quantity"));
                row.createCell(7).setCellValue(rs.getDouble("price"));
                row.createCell(8).setCellValue(rs.getDouble("discount"));
                row.createCell(9).setCellValue(rs.getString("description"));
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
    }

}
