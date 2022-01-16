package com.example.demo;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

public class Controller  implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addStudentButton;

    @FXML
    private Button updateStudentButton;

    @FXML
    private TextField age_field;

    @FXML
    private TextField filter_field;

    @FXML
    private TextField id_field;

    @FXML
    private TableColumn<Person, String> columnAge;

    @FXML
    private TableColumn<Person, String> columnFirstname;

    @FXML
    private TableColumn<Person, Integer> columnId;

    @FXML
    private TableColumn<Person, String> columnLastname;

    @FXML
    private Button deleteStudentButton;

    @FXML
    private TextField firstname_field;

    @FXML
    private TextField lastname_field;

    @FXML
    private TableView<Person> mainTable;

    ObservableList<Person> listA;
    ObservableList<Person> dataList;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    void selectStudent() {
        index = mainTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        id_field.setText(columnId.getCellData(index).toString());
        firstname_field.setText(columnFirstname.getCellData(index).toString());
        lastname_field.setText(columnLastname.getCellData(index).toString());
        age_field.setText(columnAge.getCellData(index).toString());

    }

    public void addPerson(){
        conn = DatabaseHandler.getDbConnection();
        String sql = "insert into student(firstname, lastname, age) values(?,?,?)";

        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1, firstname_field.getText());
            pst.setString(2, lastname_field.getText());
            pst.setString(3, age_field.getText());
            pst.execute();

            UpdateTable();
            searchUser();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(){
        conn = DatabaseHandler.getDbConnection();
        String sql = "delete from student where idstudent = ?";

        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, id_field.getText());
            pst.execute();

            UpdateTable();
            searchUser();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(){
        try{
            conn = DatabaseHandler.getDbConnection();
            String value1 = id_field.getText();
            String value2 = firstname_field.getText();
            String value3 = lastname_field.getText();
            String value4 = age_field.getText();

            String sql = "update student set idstudent= '" + value1
                    + "', firstname= '" + value2
                    + "', lastname= '" + value3
                    + "', age= '" + value4
                    + "' where idstudent= '" + value1 + "' ";

            pst = conn.prepareStatement(sql);
            pst.execute();

            UpdateTable();
            searchUser();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void searchUser(){
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<Person, String>("firstname"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<Person, String>("lastname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));

        dataList = DatabaseHandler.getDataperson();
        mainTable.setItems(dataList);

        FilteredList<Person> filterData = new FilteredList<>(dataList, b -> true);

        filter_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(person -> {

                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue. toLowerCase();
                if(person.getFirstname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(person.getLastname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(String.valueOf(person.getAge()).indexOf(lowerCaseFilter) != -1){
                    return true;
                } else
                    return false;
            });
        });

        SortedList<Person> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(mainTable.comparatorProperty());
        mainTable.setItems(sortedData);

    }

    public void UpdateTable(){
        columnId.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        columnFirstname.setCellValueFactory(new PropertyValueFactory<Person, String>("firstname"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<Person, String>("lastname"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));

        listA = DatabaseHandler.getDataperson();
        mainTable.setItems(listA);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        UpdateTable();
        searchUser();
    }

}
