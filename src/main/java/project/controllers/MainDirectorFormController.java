package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import project.Employee;
import project.MySQL;

import javax.imageio.ImageIO;

public class MainDirectorFormController {

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
    private JFXButton employeesButton;

    @FXML
    private JFXButton incomeButton;

    @FXML
    private TextField nameField;

    @FXML
    private JFXButton ordersButton;

    @FXML
    private JFXButton photoButton;

    @FXML
    private TextField positionFiled;

    @FXML
    private JFXButton productsButton;

    @FXML
    private TextField salaryField;

    @FXML
    private JFXButton salesButton;

    @FXML
    private Pane staffPane;

    @FXML
    private TextField surnameField;

    @FXML
    private JFXButton updateButton;


    @FXML
    public void update(){
        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String birthdate = birthdateField.getText();
        String position = positionFiled.getText();
        double salary = Double.parseDouble(salaryField.getText());
//        String path = avatarImg.getTag().toString();


        int ID = authController.id;

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
    }

    @FXML
    void initialize() {
        salesButton.setText(String.valueOf(getTotal("quantity","sales")));
        incomeButton.setText(String.valueOf(getTotalDouble("total_price","sales")));
        ordersButton.setText(String.valueOf(getTotal("quantity","orders")));
        productsButton.setText(String.valueOf(getTotal("quantity","products")));
        employeesButton.setText(String.valueOf(getCount("id","employees")));

        File file = new File(Employee.getImgPath(authController.id));
        Image image = new Image(file.toURI().toString());
        avatarImg.setImage(image);

        FieldsFill(authController.id);
    }

    public void FieldsFill(int id){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *, position FROM employees JOIN positions ON employees.position_id=positions.id WHERE employees.id = "+id);
            while (rs.next()){
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String address = rs.getString("address");
                String birthdate = rs.getString("birthdate");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");

                nameField.setText(name);
                surnameField.setText(surname);
                addressField.setText(address);
                birthdateField.setText(birthdate);
                positionFiled.setText(position);
                salaryField.setText(String.valueOf(salary));
            }
        }catch (SQLException e){
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

    @FXML
    void UpdateAvatar(){
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(photoButton.getScene().getWindow());
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            String img_path = file.getAbsolutePath();
            System.out.println(img_path);
//            File f1 = new File(img_path);
//            Image image1 = new Image(f1.toURI().toString());
            avatarImg.setImage(image);
            Employee.UpdateEmployeeAvatar(authController.id,img_path);
//            workbook.write(fileOutputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public int getTotal(String column, String table) {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        int total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM("+column+") FROM "+table);
            if (rs.next()) {
                total = rs.getInt(1);
                System.out.println(total);
            }
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
        return total;
    }
    public double getTotalDouble(String column, String table) {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        double total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM("+column+") FROM "+table);
            if (rs.next()) {
                total = rs.getDouble(1);
                System.out.println(total);
            }
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
        return total;
    }

    public int getCount(String column, String table){
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        int total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT("+column+") FROM "+table);
            if (rs.next()) {
                total = rs.getInt(1);
                System.out.println(total);
            }
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
        return total;
    }
}
