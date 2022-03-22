package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Delivery extends Order{
    private int deliver_id;

    public Delivery(int deliver_id, int order_id, int product_id, String product, String customer_name, String address, String mail, int phonenumber, int quantity, String order_time) {
        super(order_id, product_id, product, customer_name, address, mail, phonenumber, quantity, order_time);
        this.deliver_id = deliver_id;
    }

    public int getDeliver_id() {
        return deliver_id;
    }

    public void setDeliver_id(int deliver_id) {
        this.deliver_id = deliver_id;
    }

    public static ObservableList<Delivery> data(){
        ObservableList<Delivery> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT deliveryinprogress.*,(SELECT CONCAT(product.name, ' ', product.model) FROM product WHERE product.product_id = deliveryinprogress.product_id) AS product,(SELECT orders.mail FROM orders WHERE deliveryinprogress.order_id = orders.order_id) AS mail, (SELECT orders.customer_name FROM orders WHERE deliveryinprogress.order_id = orders.order_id) AS customer_name, (SELECT orders.phonenumber FROM orders WHERE deliveryinprogress.order_id = orders.order_id) AS phonenumber FROM deliveryinprogress");
            while (rs.next()) {
                oblist.add(new Delivery(rs.getInt("delivery_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getString("address"),
                        rs.getString("mail"),
                        rs.getInt("phonenumber"),
                        rs.getInt("quantity"),
                        rs.getString("order_time")));
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
