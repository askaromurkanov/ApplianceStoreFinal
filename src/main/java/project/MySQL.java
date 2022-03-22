//package project;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//
//public class MySQL {
//    static Connection conn;
//    public static final String HOST = "localhost:3306";
//    public static final String DB = "techno";
//    public static final String USER = "root";
//    public static final String PASS = "root";
//
//
//    public static Connection DBConnect() {
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+DB, USER, PASS);
//            return conn;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//}

package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/techno?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";


    public static Connection DBConnect(){
        try{ Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
