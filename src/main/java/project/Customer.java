package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Customer extends User {
    private int phonenumber;
    private String mail;
    private int purchases;


    public Customer(int id, String name, String surname, String mail, int phonenumber, String address, int purchases, String username, String password) {
        super(id, name, surname, address, username, password);
        this.phonenumber = phonenumber;
        this.mail = mail;
        this.purchases=purchases;
    }

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStringValurOfPhonenumber(){
        String phonenumber = String.valueOf(this.phonenumber);
        return phonenumber;
    }



    public static ObservableList<Customer> dataForTable(){
        ObservableList<Customer> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customers.*,(SELECT SUM(sales.quantity) FROM sales WHERE sales.customer_id = customers.customer_id) AS purchases FROM customers");
            while (rs.next()) {
                oblist.add(new Customer(rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getInt("phonenumber"),
                        rs.getString("address"),
                        rs.getInt("purchases"),
                        rs.getString("login"),
                        rs.getString("password")));
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
        return oblist;
    }

}
