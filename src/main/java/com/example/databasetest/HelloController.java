package com.example.databasetest;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private ComboBox<Integer> years;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private TableView<SeismicEvent> tableView;

    @FXML
    private TableColumn<SeismicEvent, Integer> idColumn;
    @FXML
    private TableColumn<SeismicEvent, Integer> yearColumn;
    @FXML
    private TableColumn<SeismicEvent, Integer> magnitudeColumn;
    @FXML
    private TableColumn<SeismicEvent, Integer> eventCountColumn;
    @FXML
    private Button viewTable;
    @FXML
    private Button loadInfo;
    private Connection con;

    //method to change the .fxml file
    @FXML
    private void handleViewTableButtonClick(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseTester.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //method to handle table view information and display in the database
    @FXML
    private void handleLoadInfoButtonClick(ActionEvent event) {
        try {

            int selectedYear = years.getValue();


            String sql = "SELECT id, year, magnitude, event_count FROM SISMICEVENTS.SeismicMagnitude WHERE year = ?";


            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, selectedYear);
                ResultSet rs = stmt.executeQuery();

                //clear table
                tableView.getItems().clear();

                // Observablelist to populate the table
                ObservableList<SeismicEvent> data = FXCollections.observableArrayList();

                //loop trought the data( id , year, magnitude, event count)
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int year = rs.getInt("year");
                    int magnitude = rs.getInt("magnitude");
                    int eventCount = rs.getInt("event_count");

                    data.add(new SeismicEvent(id, year, magnitude, eventCount));
                }


                idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
                yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
                magnitudeColumn.setCellValueFactory(new PropertyValueFactory<>("magnitude"));
                eventCountColumn.setCellValueFactory(new PropertyValueFactory<>("eventCount"));

                // Printing data to console
                for (SeismicEvent events : data) {
                    System.out.println(events.getID() + ", " + events.getYear() + ", " + events.getMagnitude() + ", " + events.getEventCount());
                }

                // Populate the TableView with the data -- to display it in the table
                tableView.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void initialize() {
        // Populate the ComboBox with years from 2010 to 2022
        for (int year = 2010; year <= 2022; year++) {
            years.getItems().add(year);
        }



        // Set up the database connection
        String url = "PATH/TO/FILE";
        String uname = "root";
        String pass = "YOUR_PASSWORD";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, uname, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection error");
            e.printStackTrace();
        }



        // Set up the BarChart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Magnitude");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Cases");

//        BarChart barChart = new BarChart(xAxis,yAxis);
//        XYChart.Series dataSeries =  new XYChart.Series();
//        dataSeries.setName("Sismics Events");
// Register an event listener for the ComboBox selection changex

        viewTable.setOnAction(event -> handleViewTableButtonClick(event));
        loadInfo.setOnAction(event -> handleLoadInfoButtonClick(event));

        years.setOnAction(event -> {
            int selectedYear = years.getValue();
            String sqlMagnitude8 = "SELECT magnitude, event_count FROM SeismicMagnitude WHERE year = ? AND magnitude = 8";
            System.out.println(selectedYear);
                    try (PreparedStatement stmtMagnitude8 = con.prepareStatement(sqlMagnitude8)) {
                        stmtMagnitude8.setInt(1, selectedYear);
                        ResultSet rsMagnitude8 = stmtMagnitude8.executeQuery();


                        barChart.getData().clear();

                        XYChart.Series<String, Number> seriesMagnitude8 = new XYChart.Series<>();
                        seriesMagnitude8.setName("Magnitude 8");


                        while (rsMagnitude8.next()) {
                            int eventCount = rsMagnitude8.getInt("event_count");
                            seriesMagnitude8.getData().add(new XYChart.Data<>("Magnitude 8", eventCount));
                        }


                        barChart.getData().add(seriesMagnitude8);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
            String sqlMagnitude7 = "SELECT magnitude, event_count FROM SeismicMagnitude WHERE year = ? AND magnitude = 7";
            try (PreparedStatement stmtMagnitude7 = con.prepareStatement(sqlMagnitude7)) {
                stmtMagnitude7.setInt(1, selectedYear);
                ResultSet rsMagnitude7 = stmtMagnitude7.executeQuery();


                XYChart.Series<String, Number> seriesMagnitude7 = new XYChart.Series<>();
                seriesMagnitude7.setName("Magnitude 7");


                while (rsMagnitude7.next()) {
                    int eventCount = rsMagnitude7.getInt("event_count");
                    seriesMagnitude7.getData().add(new XYChart.Data<>("Magnitude 7", eventCount));
                }

                barChart.getData().add(seriesMagnitude7);
            }catch (SQLException e) {
                System.out.println("Error executing SQL query");
                e.printStackTrace();
            }
            String sqlMagnitude6 = "SELECT magnitude, event_count FROM SeismicMagnitude WHERE year = ? AND magnitude = 6";
            try (PreparedStatement stmtMagnitude6 = con.prepareStatement(sqlMagnitude6)) {
                stmtMagnitude6.setInt(1, selectedYear);
                ResultSet rsMagnitude6 = stmtMagnitude6.executeQuery();


                XYChart.Series<String, Number> seriesMagnitude6 = new XYChart.Series<>();
                seriesMagnitude6.setName("Magnitude 6");


                while (rsMagnitude6.next()) {
                    int eventCount = rsMagnitude6.getInt("event_count");
                    seriesMagnitude6.getData().add(new XYChart.Data<>("Magnitude 6", eventCount));
                }


                barChart.getData().add(seriesMagnitude6);
            }catch (SQLException e) {
                System.out.println("Error executing SQL query");
                e.printStackTrace();
            }
            String sqlMagnitude5 = "SELECT magnitude, event_count FROM SeismicMagnitude WHERE year = ? AND magnitude = 5";
            try (PreparedStatement stmtMagnitude5 = con.prepareStatement(sqlMagnitude5)) {
                stmtMagnitude5.setInt(1, selectedYear);
                ResultSet rsMagnitude5 = stmtMagnitude5.executeQuery();


                XYChart.Series<String, Number> seriesMagnitude5 = new XYChart.Series<>();
                seriesMagnitude5.setName("Magnitude 5");


                while (rsMagnitude5.next()) {
                    int eventCount = rsMagnitude5.getInt("event_count");
                    seriesMagnitude5.getData().add(new XYChart.Data<>("Magnitude 5", eventCount));
                }


                barChart.getData().add(seriesMagnitude5);
            }catch (SQLException e) {
                System.out.println("Error executing SQL query");
                e.printStackTrace();
            }
            String sqlMagnitude4 = "SELECT magnitude, event_count FROM SeismicMagnitude WHERE year = ? AND magnitude = 4";
            try (PreparedStatement stmtMagnitude4 = con.prepareStatement(sqlMagnitude4)) {
                stmtMagnitude4.setInt(1, selectedYear);
                ResultSet rsMagnitude4 = stmtMagnitude4.executeQuery();


                XYChart.Series<String, Number> seriesMagnitude4 = new XYChart.Series<>();
                seriesMagnitude4.setName("Magnitude 4");


                while (rsMagnitude4.next()) {
                    int eventCount = rsMagnitude4.getInt("event_count");
                    seriesMagnitude4.getData().add(new XYChart.Data<>("Magnitude 4", eventCount));
                }


                barChart.getData().add(seriesMagnitude4);
            }catch (SQLException e) {
                System.out.println("Error executing SQL query");
                e.printStackTrace();
            }
        });
    }
}
