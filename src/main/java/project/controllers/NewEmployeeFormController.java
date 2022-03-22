package project.controllers;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import project.Employee;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewEmployeeFormController {
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

    String img_path = "D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\defaultavatar.jpg";

    @FXML
    void initialize() {
        table();
        search();
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
    void NewPhoto(){
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(tableEmployees.getScene().getWindow());
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            img_path = file.getAbsolutePath();
            System.out.println(img_path);
            avatarImg.setImage(image);

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(img_path);
    }




    @FXML
    public void save(){
        String defaultLogin="log";
        String defaultPassword="pass";
//        String image = NewPhoto();
        Connection conn = project.MySQL.DBConnect();
        try {
            PreparedStatement ps;
            String sql_insert = "INSERT INTO employees (`name`, `surname`, `address`, `birthdate`, `position`, `salary`, `username`, `password`, `image`) VALUES (?,?,?,?,?,?,?,?,?)";
            assert conn != null;

            ps = conn.prepareStatement(sql_insert);

            ps.setString(1, nameField.getText());
            ps.setString(2, surnameField.getText());
            ps.setString(3, addressField.getText());
            ps.setString(4, birthdateField.getText());
            ps.setString(5, positionFiled.getText());
            ps.setDouble(6, Double.parseDouble(salaryField.getText()));
            ps.setString(7, defaultLogin);
            ps.setString(8, defaultPassword);
            ps.setString(9, img_path);

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
        nameField.setText(null);
        surnameField.setText(null);
        addressField.setText(null);
        birthdateField.setText(null);
        positionFiled.setText(null);
        salaryField.setText(null);

        File file = new File(img_path);
        Image img = new Image(file.toURI().toString());
        avatarImg.setImage(img);
        table();
    }

    @FXML
    public void clean(){
        nameField.setText(null);
        surnameField.setText(null);
        addressField.setText(null);
        birthdateField.setText(null);
        positionFiled.setText(null);
        salaryField.setText(null);

        File file = new File("D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\defaultavatar.png");
        Image image = new Image(file.toURI().toString());
        avatarImg.setImage(image);
    }
}

