package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import project.Employee;
import project.MySQL;

import javax.imageio.ImageIO;

public class employeesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;
    @FXML
    private ImageView avatarImg;
    @FXML
    private TextField birthdateField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField positionFiled;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField searchField;
    @FXML
    private TextField surnameField;


    @FXML
    private TableView<Employee> tableEmployees;
    @FXML
    private TableColumn<Employee, Integer> id_col;
    @FXML
    private TableColumn<Employee, String> name_col;
    @FXML
    private TableColumn<Employee, String> surname_col;
    @FXML
    private TableColumn<Employee, String> address_col;
    @FXML
    private TableColumn<Employee, String> birthdate_col;
    @FXML
    private TableColumn<Employee, String> position_col;
    @FXML
    private TableColumn<Employee, Double> salary_col;
    @FXML
    private TableColumn<Employee, String> username_col;
    @FXML
    private TableColumn<Employee, String> password_col;

    Employee employee;

    @FXML
    void initialize() {
        table();
        search();
        System.out.println(avatarImg.getImage());
        File file = new File(String.valueOf(avatarImg));
//        BufferedImage bufferedImage = null;
//        try {
//            bufferedImage = ImageIO.read(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        String img_path = file.getAbsolutePath();
        System.out.println(img_path);
//        Image image = new Image(String.valueOf(avatarImg));
//        System.out.println(image.get);
    }

    private void table(){
        ObservableList<Employee> oblist = Employee.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        surname_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        address_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("address"));
        birthdate_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("birthdate"));
        position_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        salary_col.setCellValueFactory(new PropertyValueFactory<Employee,Double>("salary"));
        username_col.setCellValueFactory(new PropertyValueFactory<Employee, String >("username"));
        password_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("password"));

        tableEmployees.setItems(oblist);
    }

    void search(){
        ObservableList<Employee> oblist = Employee.dataForTable();
        id_col.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        surname_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        address_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("address"));
        birthdate_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("birthdate"));
        position_col.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        salary_col.setCellValueFactory(new PropertyValueFactory<Employee,Double>("salary"));
        username_col.setCellValueFactory(new PropertyValueFactory<Employee, String >("username"));
        password_col.setCellValueFactory(new PropertyValueFactory<Employee,String>("password"));

        tableEmployees.setItems(oblist);

        FilteredList<Employee> filteredData = new FilteredList<>(oblist, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getStringValueOfID().contains(lowerCaseFilter)) {
                    return true;
                }else if (employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (employee.getStringValueOfSalary().contains(lowerCaseFilter)){
                    return true;
                }else if(employee.getSurname().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(employee.getAddress().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(employee.getBirthdate().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableEmployees.comparatorProperty());
        tableEmployees.setItems(sortedData);
    }
    @FXML
    int getSelected(){
        int index = -1;

        index = tableEmployees.getSelectionModel().getSelectedIndex();

        File file = new File(Employee.getImgPath(id_col.getCellData(index)));
        Image image = new Image(file.toURI().toString());
        avatarImg.setImage(image);


        nameField.setText(name_col.getCellData(index));
        surnameField.setText(surname_col.getCellData(index));
        addressField.setText(address_col.getCellData(index));
        birthdateField.setText(birthdate_col.getCellData(index));
        positionFiled.setText(position_col.getCellData(index));
        salaryField.setText(salary_col.getCellData(index).toString());

        return index;
    }

    @FXML
    public void delete(){
        int index = getSelected();
        int id = id_col.getCellData(index);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm the employee's dismissal");
        alert.setHeaderText("Are you sure you want to dismiss an employee");
        alert.setContentText("If you click OK, all the data of this employee will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Employee.deleteEmployee(id);
            table();
            search();
        }
    }

    @FXML
    public void update(){

        int index = -1;

        index = tableEmployees.getSelectionModel().getSelectedIndex();

        if (index<=-1) {
            return;
        }

        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String birthdate = birthdateField.getText();
        String position = positionFiled.getText();
        double salary = Double.parseDouble(salaryField.getText());
//        String path = avatarImg.getTag().toString();


        int ID = id_col.getCellData(getSelected());

        Connection conn = MySQL.DBConnect();
        PreparedStatement ps = null;
        try {
            String sql = ("UPDATE employees SET name = ?, surname = ?, address = ?, birthdate= ?, position= ?, salary= ? WHERE ID = "+ID);
            ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, address);
            ps.setString(4, birthdate);
            ps.setString(5, position);
            ps.setDouble(6, salary);

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
    }
    @FXML
    public void clean(){
        nameField.setText(null);
        surnameField.setText(null);
        addressField.setText(null);
        birthdateField.setText(null);
        positionFiled.setText(null);
        salaryField.setText(null);
    }

    @FXML
    void UpdateAvatar(){
        int index = getSelected();
        int id = id_col.getCellData(index);

        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);


            File file = fileChooser.showOpenDialog(tableEmployees.getScene().getWindow());
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            String img_path = file.getAbsolutePath();
            System.out.println(img_path);
//            File f1 = new File(img_path);
//            Image image1 = new Image(f1.toURI().toString());
            avatarImg.setImage(image);
            Employee.UpdateEmployeeAvatar(id,img_path);
//            workbook.write(fileOutputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
