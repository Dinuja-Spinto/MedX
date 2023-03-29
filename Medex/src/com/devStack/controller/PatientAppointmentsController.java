package com.devStack.controller;

import com.devStack.utill.Cookie;
import com.devStack.utill.CrudUtil;
import com.devStack.view.tm.AllAppointmentTm;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientAppointmentsController {
    public AnchorPane appointmentContext;
    public JFXDatePicker txtDateFrom;
    public JFXDatePicker txtDateTo;
    public JFXRadioButton rBtnAll;
    public ToggleGroup type;
    public JFXRadioButton rBtnPending;
    public JFXRadioButton rBtnCompleted;
    public TableView tblAppointment;
    public TableColumn colId;
    public TableColumn colPatient;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colAmount;
    public TableColumn colState;
    public TableColumn colManage;
    private String selectedDoctorId = "";

    public StringBuilder sql = new StringBuilder();

    private boolean isSearch = false;

    public void initialize() {
        loadDoctorData(); 
        loadAppointments();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colManage.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colState.setCellValueFactory(new PropertyValueFactory<>("checkState"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        rBtnPending.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue) {
                if (newValue){
                    loadAppointments();
                }
            }
        });
        rBtnCompleted.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                loadAppointments();
            }
        });
        rBtnAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue, Boolean newValue) {
                if (newValue){
                    loadAppointments();
                }
            }
        });

    }

    private void loadDoctorData() {
        try {
            ResultSet set = CrudUtil.execute("SELECT doctor_id FROM doctor WHERE email=?",
                    Cookie.selectedUser.getEmail());
            if (set.next()) {
                selectedDoctorId = set.getString(1);
            } else {
                setUi("DoctorRegistrationForm");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DoctorDashboard");
    }

    public void searchData(ActionEvent actionEvent) {
        isSearch =true;
        sql = new StringBuilder();
        this.loadAppointments();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) appointmentContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
    private void loadAppointments() {
        ObservableList<AllAppointmentTm> tmList = FXCollections.observableArrayList();

        sql.append("SELECT a.*, p.first_name,p.last_name FROM appointment a JOIN patient p ON a.doctor_id=? && p.patient_id=a.patient_id");
        if (rBtnPending.isSelected()) {
            sql.append("SELECT a.*, p.first_name,p.last_name FROM appointment a JOIN patient p ON a.doctor_id=? AND a.check_state=0 && p.patient_id=a.patient_id");
        } else if (rBtnCompleted.isSelected()) {
            sql.append("SELECT a.*, p.first_name,p.last_name FROM appointment a JOIN patient p ON a.doctor_id=? AND a.check_state=1 && p.patient_id=a.patient_id");
        }
        // check from date & to date Select ?
        if(isSearch){
            sql.append(" WHERE a.date BETWEEN \'"+txtDateFrom.getValue()+ "\'AND \'"+txtDateTo.getValue()+"\'");
        }
        try {
            ResultSet set = CrudUtil.execute(sql.toString(), selectedDoctorId);

            while (set.next()){
                Button btn = new Button("manage");
                AllAppointmentTm tm = new AllAppointmentTm(
                        set.getString(1),
                        set.getString("first_name")+" "+set.getString("last_name"),
                        set.getString(2),
                        set.getString(3),
                        set.getDouble(4),
                        set.getInt(5)==0?"Pending":"Completed",
                        btn
                );
                tmList.add(tm);
            }
            tblAppointment.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
