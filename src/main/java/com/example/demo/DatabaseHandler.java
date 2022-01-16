package com.example.demo;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHandler{
 Connection conn = null;

 public static Connection getDbConnection() {
     try {
         Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/students",
                 "root",
                 "solevarr");
         return conn;
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return null;
 }

    public static ObservableList<Person> getDataperson() {

        Connection conn = getDbConnection();
        ObservableList<Person> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Person(Integer.parseInt(rs.getString("idstudent")),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("age")));
            }
        }catch (Exception e){

        }

        return list;
    }

}
