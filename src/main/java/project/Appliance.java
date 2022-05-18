package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Appliance {
    private int id;
    private String name;
    private String model;
    private String factory;
    private String category;
    private int year;
    private int quantity;
    private double price;
    private double discount;
    private String description;

    public Appliance(int id, String name, String model, String factory, String category, int year, int quantity, double price, double discount, String description) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.factory = factory;
        this.category = category;
        this.year = year;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.description = description;
    }

    public Appliance(int id, String name, String model, String factory, String category, int quantity) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.factory = factory;
        this.category = category;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getStringValueOfID(){
        String id = String.valueOf(this.id);
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public String getStringValueOfYear(){
        String year = String.valueOf(this.year);
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStringValueOfQuantity(){
        String quantity = String.valueOf(this.quantity);
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ObservableList<Appliance> dataForTable(){
        ObservableList<Appliance> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product_id, appliance, model, manufacturers.manufacturer, categories.category," +
                    " year, quantity, price, discount, description " +
                    "FROM products JOIN manufacturers ON products.manufacturer_id = manufacturers.id " +
                    "JOIN categories ON products.category_id = categories.id");
            while (rs.next()) {
                oblist.add(new Appliance(rs.getInt("product_id"),
                        rs.getString("appliance"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getInt("year"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("discount"),
                        rs.getString("description")));
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

    public static String getDescription(int ID){
        Connection conn = MySQL.DBConnect();
        String description = "Great Appliance";
        try {
            PreparedStatement ps;
            String sql_check = "SELECT description FROM products WHERE product_id = ?";
            ps = conn.prepareStatement(sql_check);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                description=rs.getString(1);
            }
            if (description == null){
                description="Great Appliance";
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
        return description;
    }

    public static void deleteAppliance(int id){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            PreparedStatement ps;
            String sql_insert = "DELETE FROM products WHERE product_id = "+id;
            ps = conn.prepareStatement(sql_insert);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UpdateProductImage(int ID, String imgPath){
        Connection conn = MySQL.DBConnect();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement("UPDATE products SET image = ? WHERE product_id = "+ID);
            ps.setString(1,imgPath);
            ps.execute();
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
    public static String getImgPath(int ID){
        Connection conn = MySQL.DBConnect();
        String imgPath = "D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png";
        try {
            PreparedStatement ps;
            String sql = "SELECT image FROM products WHERE product_id = ?";
            assert conn != null;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                imgPath=rs.getString(1);
            }
            if (imgPath == null){
                imgPath="D:\\Учеба\\Курсавуха\\src\\project\\images\\product\\electrical-appliance.png";
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
        return imgPath;
    }



    public static ObservableList<Appliance> getDataForReport(String table, String column){
        ObservableList<Appliance> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();

        LocalDate todayDate = LocalDate.now();
        LocalDate yesterday = todayDate.minusDays(1);
        String strYesterday = String.valueOf(yesterday);

        String sql = "SELECT "+table+".product_id, products.appliance, products.model, categories.category, " +
                "manufacturers.manufacturer, SUM("+table+".quantity) AS quantity " +
                "FROM "+table+" JOIN products ON "+table+".product_id = products.product_id " +
                "JOIN categories ON products.category_id = categories.id " +
                "JOIN manufacturers ON products.manufacturer_id = manufacturers.id " +
                "WHERE "+table+"." + column+"= '"+strYesterday+"' "+
                "GROUP BY "+table+".product_id " +
                "ORDER BY quantity DESC LIMIT 50";
        try {
            assert conn != null;


            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oblist.add(new Appliance(rs.getInt("product_id"),
                        rs.getString("appliance"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getInt("quantity")));
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

    public static ObservableList<Appliance> getDataForReport(String table, String date_column, String day){
        ObservableList<Appliance> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();

        String sql = "SELECT "+table+".product_id, products.appliance, products.model, categories.category, " +
                "manufacturers.manufacturer, SUM("+table+".quantity) AS quantity " +
                "FROM "+table+" JOIN products ON "+table+".product_id = products.product_id " +
                "JOIN categories ON products.category_id = categories.id " +
                "JOIN manufacturers ON products.manufacturer_id = manufacturers.id " +
                "WHERE "+table+"."+date_column+" = ?"+
                "GROUP BY "+table+".product_id " +
                "ORDER BY quantity DESC LIMIT 15";
        try {
            assert conn != null;


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, day);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oblist.add(new Appliance(rs.getInt("product_id"),
                        rs.getString("appliance"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getInt("quantity")));
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

    public static ObservableList<Appliance> getDataForReport(String table, int month, int year){
        ObservableList<Appliance> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();


        String sql = "SELECT "+table+".product_id, products.appliance, products.model, categories.category, " +
                "manufacturers.manufacturer, SUM("+table+".quantity) AS quantity " +
                "FROM "+table+" JOIN products ON "+table+".product_id = products.product_id " +
                "JOIN categories ON products.category_id = categories.id " +
                "JOIN manufacturers ON products.manufacturer_id = manufacturers.id " +
                "WHERE "+table+".month = ? AND "+table+".year = ? " +
                "GROUP BY "+table+".product_id " +
                "ORDER BY quantity DESC LIMIT 15";
        try {
            assert conn != null;


            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oblist.add(new Appliance(rs.getInt("product_id"),
                        rs.getString("appliance"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getInt("quantity")));
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

    public static ObservableList<Appliance> getDataForReport(String table, int year){
        ObservableList<Appliance> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();


        String sql = "SELECT "+table+".product_id, products.appliance, products.model, categories.category, " +
                "manufacturers.manufacturer, SUM("+table+".quantity) AS quantity " +
                "FROM "+table+" JOIN products ON "+table+".product_id = products.product_id " +
                "JOIN categories ON products.category_id = categories.id " +
                "JOIN manufacturers ON products.manufacturer_id = manufacturers.id " +
                "WHERE "+table+".year = ? " +
                "GROUP BY "+table+".product_id " +
                "ORDER BY quantity DESC LIMIT 15";
        try {
            assert conn != null;


            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oblist.add(new Appliance(rs.getInt("product_id"),
                        rs.getString("appliance"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("category"),
                        rs.getInt("quantity")));
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

    public static ObservableList<Integer> getDateValues(String type, String table){
        ObservableList<Integer> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT "+type+" FROM "+table+" ORDER BY "+type);
            while (rs.next()) {
                oblist.add(rs.getInt(1));
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
