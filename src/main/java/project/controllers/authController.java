package project.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import project.Main;
import project.MySQL;



public class authController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameFiled;
    @FXML
    private Label invalidMessage;

    static int id;
    String name;
    String surname;
    String position;
    String img;

    public String getImg() {
        return img;
    }

    @FXML
    private void initialize() {
        MySQL.DBConnect();
        invalidMessage.setVisible(false);

        signInButton.setOnAction(event -> {
            String username=usernameFiled.getText().trim();
            String password = passwordField.getText().trim();

            Connection conn = MySQL.DBConnect();
            PreparedStatement ps;
            String sql = "SELECT employees.id, name, surname, position, username, password FROM employees JOIN positions ON employees.position_id = positions.id WHERE username = ? AND password =? ";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    id=rs.getInt("id");
                    name=rs.getString("name");
                    surname=rs.getString("surname");
                    position=rs.getString("position");
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
            System.out.println(position);

            if(position!=null && position.equals("director")) {
                signInButton.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/DirectorAccount.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle("worker");
                stage.setScene(scene);
                stage.show();
            }
            else if (position!=null && position.equals("worker")){
                signInButton.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/workerAccount.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle("worker");
                stage.setScene(scene);
                stage.show();
            }
            else if (position!=null && position.equals("deliveryman")){
                signInButton.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/deliverymanAccount.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle("worker");
                stage.setScene(scene);
                stage.show();
            }
            else{
                System.out.println("Error");
                usernameFiled.setStyle("-fx-background-color:#fafafa; -fx-border-radius:7px;-fx-border-color:#dc3545");
                passwordField.setStyle("-fx-background-color:#fafafa; -fx-border-radius:7px;-fx-border-color:#dc3545");
                usernameFiled.setText(null);
                passwordField.setText(null);
                invalidMessage.setVisible(true);
            }

        });
        signUpButton.setOnAction(event ->{
            signUpButton.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/registration.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("worker");
            stage.setScene(scene);
            stage.show();
        });

    }
}
