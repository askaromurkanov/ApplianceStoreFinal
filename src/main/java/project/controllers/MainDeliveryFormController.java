package project.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import project.Employee;
import project.MySQL;

public class MainDeliveryFormController {

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
    private JFXButton deliveriesButton;

    @FXML
    private JFXButton lastdateButton;

    @FXML
    private TextField nameField;

    @FXML
    private JFXButton ordersButton;

    @FXML
    private JFXButton positionButton;

    @FXML
    private JFXButton salaryButton;

    @FXML
    private Pane staffPane;

    @FXML
    private TextField surnameField;

    @FXML
    void initialize() {
        deliveriesButton.setText(String.valueOf(getTotalWhere("quantity","sales")));
        salaryButton.setText(String.valueOf(salary(authController.id)));

        String datetime = strFromTabel("MAX(sale_time)","sales","employee_id");
        String date = "NO DELIVERIES";
        if (datetime != null){
            date = datetime.substring(0,10);
        }
        lastdateButton.setText(date);
        //lastdateButton.setText(strFromTabel("MAX(sale_time)","sales","employee_id"));
        System.out.println(strFromTabel("MAX(sale_time)","sales","employee_id"));
        ordersButton.setText(String.valueOf(getTotal("quantity","orders")));
        positionButton.setText(strFromTabel("position","employees","id"));

        File file = new File(Employee.getImgPath(authController.id));
        Image image = new Image(file.toURI().toString());
        avatarImg.setImage(image);

        FieldsFill(authController.id);

    }

    private void FieldsFill(int id){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees WHERE id = "+id);
            while (rs.next()){
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String address = rs.getString("address");
                String birthdate = rs.getString("birthdate");

                nameField.setText(name);
                surnameField.setText(surname);
                addressField.setText(address);
                birthdateField.setText(birthdate);

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

    private double salary(int id){
        double salary = 0;
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT salary FROM employees WHERE id = "+id);
            while (rs.next()){
                salary = rs.getDouble(1);
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
        return salary;
    }

    private String strFromTabel(String column, String table, String idColumn){
        String result = "";
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT "+column+" FROM "+table+" WHERE "+idColumn+" = "+authController.id);
            while (rs.next()){
                result = rs.getString(1);
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
        return result;
    }


    private int getTotalWhere(String column, String table) {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        int total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM("+column+") FROM "+table+" WHERE employee_id = "+authController.id);
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

    private int getTotal(String column, String table) {
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
}
