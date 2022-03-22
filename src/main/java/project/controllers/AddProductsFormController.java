package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import project.Appliance;
import project.Employee;
import project.MySQL;

import javax.imageio.ImageIO;

public class AddProductsFormController {

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

    private String img_path = "D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png";

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
    void NewPhoto(){
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(tableProducts.getScene().getWindow());
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            img_path = file.getAbsolutePath();
            System.out.println(img_path);
            avatarAppliance.setImage(image);

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(img_path);
    }

    @FXML
    void save(){
        Connection conn = project.MySQL.DBConnect();
        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO product (`name`, `model`, `factory`, `category`, `year`, `quantity`, `price`, `discount`, `description`, `image`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);

            ps.setString(1, nameField.getText());
            ps.setString(2, modelField.getText());
            ps.setString(3, factoryField.getText());
            ps.setString(4, categoryField.getText());
            ps.setInt(5, Integer.parseInt(yearField.getText()));
            ps.setInt(6, Integer.parseInt(quantityField.getText()));
            ps.setDouble(7, Double.parseDouble(priceField.getText()));
            ps.setDouble(8, Double.parseDouble(discountField.getText()));
            ps.setString(9, descriptiontextArea.getText());
            ps.setString(10, img_path);

            ps.execute();
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
        clean();

        table();
    }

    @FXML
    void clean(){
        File file = new File("D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png");
        Image image = new Image(file.toURI().toString());
        avatarAppliance.setImage(image);

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
}
