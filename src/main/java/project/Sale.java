package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sale {
    private int sale_id;
    private String product;
    private String customer_name;
    private int employee_id;
    private int delivery;
    private int quantity;
    private double total_price;
    private String sale_time;

    public Sale(int sale_id, String product, String customer_name, int employee_id, int delivery, int quantity, double total_price, String sale_time) {
        this.sale_id = sale_id;
        this.product = product;
        this.customer_name = customer_name;
        this.employee_id = employee_id;
        this.delivery = delivery;
        this.quantity = quantity;
        this.total_price = total_price;
        this.sale_time = sale_time;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public String getStringValueOfID() {
        String sale_id = String.valueOf(this.sale_id);
        return sale_id;
    }

    public String getStringValurOfEmployeeID() {
        String id = String.valueOf(this.employee_id);
        return id;
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

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getQuality() {
        return quantity;
    }

    public void setQuality(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getSale_time() {
        return sale_time;
    }

    public void setSale_time(String sale_time) {
        this.sale_time = sale_time;
    }

    public String getStringValueOfTotalPrice() {
        String total_price = String.valueOf(this.total_price);
        return total_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static ObservableList<Sale> dataForTable() {
        ObservableList<Sale> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sales.*,(SELECT CONCAT(product.name, ' ', product.model) FROM product WHERE product.product_id = sales.product_id) AS product FROM sales");
            while (rs.next()) {
                oblist.add(new Sale(rs.getInt("sale_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getInt("employee_id"),
                        rs.getInt("delivery"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_time")));
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

    public static ObservableList<Sale> dataForTableForEmployee(int id) {
        ObservableList<Sale> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sales.*,(SELECT CONCAT(product.name, ' ', product.model) FROM product WHERE product.product_id = sales.product_id) AS product FROM sales WHERE employee_id = "+id);
            while (rs.next()) {
                oblist.add(new Sale(rs.getInt("sale_id"),
                        rs.getString("product"),
                        rs.getString("customer_name"),
                        rs.getInt("employee_id"),
                        rs.getInt("delivery"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_time")));
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


    public static double getTotalEarningsForEmployee(int id) {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        double total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(total_price) FROM sales WHERE employee_id = "+id);
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
    public static int getTotalQuantityForEmployee(int id) {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        int total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(quantity) FROM sales WHERE employee_id = "+id);
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

    public static double getTotalEarnings() {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        double total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(total_price) FROM sales");
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
    public static int getTotalQuantity() {
        Connection conn = MySQL.DBConnect();
        Statement stmt = null;
        int total=0;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(quantity) FROM sales");
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
