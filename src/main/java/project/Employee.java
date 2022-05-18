package project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

public class Employee extends User{
    private String birthdate;
    private String position;
    private double salary;
    private int quantity;

    public Employee(int id, String name, String surname, String address, String birthdate, String position, Double salary, String username, String password) {
        super(id, name, surname, address, username, password);
        this.birthdate = birthdate;
        this.position = position;
        this.salary = salary;
    }

    public Employee(int id, String name, String surname, String address, String birthdate, String position, double salary, int quantity) {
        super(id, name, surname, address);
        this.birthdate = birthdate;
        this.position = position;
        this.salary = salary;
        this.quantity = quantity;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStringValueOfSalary() {
        String salary = String.valueOf(this.salary);
        return salary;
    }

    public static ObservableList<Employee> dataForTable(){
        ObservableList<Employee> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            System.out.println("dfdfd");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *, position FROM employees JOIN positions ON employees.position_id = positions.id");
            while (rs.next()) {
                oblist.add(new Employee(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("birthdate"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getString("username"),
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


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static ObservableList<Employee> topEmployees(){
        Date date = new Date();

        int month = date.getMonth();
        int year = date.getYear()+1990;

        ObservableList<Employee> oblist = FXCollections.observableArrayList();
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            System.out.println("dfdfd");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT employees.id, employees.name, employees.surname, employees.address, " +
                    "employees.birthdate, positions.position, employees.salary, SUM(sales.quantity) AS quantity " +
                    "FROM employees " +
                    "JOIN positions ON employees.position_id = positions.id " +
                    "JOIN sales ON employees.id = sales.employee_id " +
                    "WHERE sales.month = 5 AND sales.year = 2022 " +
                    "GROUP BY employees.id " +
                    "ORDER BY quantity DESC");
            while (rs.next()) {
                oblist.add(new Employee(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("address"),
                        rs.getString("birthdate"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
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

    public static void deleteEmployee(int ID){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            PreparedStatement ps;
            String sql_insert = "DELETE FROM employees WHERE ID = "+ID;
            ps = conn.prepareStatement(sql_insert);
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

    public static void deleteCustomer(int ID){
        Connection conn = MySQL.DBConnect();
        try {
            assert conn != null;
            PreparedStatement ps;
            String sql_insert = "DELETE FROM customers WHERE customer_id = "+ID;
            ps = conn.prepareStatement(sql_insert);
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

    public static void updateEmployee(int ID, double salary, String... columns){
        Connection conn = MySQL.DBConnect();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement("UPDATE employees SET name = ?, surname = ?, address = ?, birthdate= ?, position= ?, salary= ? WHERE ID = "+ID);
            ps.setString(1,columns[0]);
            ps.setString(2,columns[1]);
            ps.setString(3,columns[2]);
            ps.setString(4,columns[3]);
            ps.setString(5,columns[4]);
            ps.setDouble(6,salary);

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

    public static void UpdateEmployeeAvatar(int ID, String imgPath){
        Connection conn = MySQL.DBConnect();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement("UPDATE employees SET image = ? WHERE ID = "+ID);
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
        String imgPath = "D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\defaultavatar.jpg";
        try {
            PreparedStatement ps;
            String sql = "SELECT image FROM employees WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                imgPath=rs.getString(1);
            }
            if (imgPath == null){
                imgPath="D:\\Учеба\\Курсавуха\\src\\project\\images\\staff\\defaultavatar.jpg";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imgPath;
    }
}
