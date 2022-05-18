package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Order {
    private int order_id;
    private int product_id;
    private String product;
    private String customer_name;
    private String address;
    private String mail;
    private int phonenumber;
    private int quantity;
    private String order_time;


    public Order(int order_id, int product_id, String product, String customer_name, String address, String mail, int phonenumber, int quantity, String order_time) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.product = product;
        this.customer_name = customer_name;
        this.mail = mail;
        this.address = address;
        this.phonenumber = phonenumber;
        this.quantity = quantity;
        this.order_time = order_time;
    }


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrder_timer() {
        return order_time;
    }

    public void setOrder_timer(String order_timer) {
        this.order_time = order_timer;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getStringValueOfID() {
        String id = String.valueOf(this.order_id);
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStringValurOfPhonenumberID() {
        String number = String.valueOf(this.phonenumber);
        return number;
    }

    public static ObservableList<Order> dataForTable(){
        ObservableList<Order> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();

        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT orders.*,(SELECT CONCAT(products.appliance, ' ', products.model) " +
                    "FROM products WHERE products.product_id = orders.product_id) AS product " +
                    "FROM orders WHERE month > 2 AND year = 2022 LIMIT 1000");
            while (rs.next()) {
                oblist.add(new Order(rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getInt("phonenumber"),
                        rs.getInt("quantity"),
                        rs.getString("order_date")));
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
        return oblist;
    }

    public static ObservableList<Order> dataForDeliveryMan(){
        ObservableList<Order> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();

        LocalDate todayDate = LocalDate.now();
        String today = String.valueOf(todayDate);

        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT orders.*,(SELECT CONCAT(products.appliance, ' ', products.model) " +
                    "FROM products WHERE products.product_id = orders.product_id) AS product " +
                    "FROM orders WHERE order_date = '"+today+"' AND active = 1 " +
                    "ORDER BY order_date DESC " +
                    "LIMIT 300");
            while (rs.next()) {
                oblist.add(new Order(rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getInt("phonenumber"),
                        rs.getInt("quantity"),
                        rs.getString("order_date")));
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
        return oblist;
    }
}
