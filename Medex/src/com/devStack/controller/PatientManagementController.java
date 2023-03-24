package com.devStack.controller;

import com.devStack.db.Database;
import com.devStack.dto.PatientDto;
import com.devStack.view.tm.PatientTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;

public class PatientManagementController {
    public TableColumn colNic;
    public TableColumn colGender;
    public TableColumn colAge;
    public TableColumn colEmail;
    public TableView<PatientTm> tblPatient;
    public TextField txtSearch;
    public TableView tblPatients;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colDob;
    public TableColumn colAddress;

    public void initialize(){
        //loadAllData("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           // loadAllData(newValue);
        });

        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("genderType"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void loadAllData(String s) {
        s = s.toLowerCase(); // immutable
        ObservableList<PatientTm> tmList = FXCollections.observableArrayList();
        //==> filter
        for (PatientDto dto : Database.patientTable
        ) {
            if (
                    dto.getfName().contains(s) ||
                            dto.getlName().contains(s) ||
                            dto.getEmail().contains(s)
            ) {
                tmList.add(
                        new PatientTm(
                                dto.getNic(),
                                dto.getfName(),
                                dto.getlName(),
                                new SimpleDateFormat("yyyy-MM-dd")
                                        .format(dto.getDbo()),
                                dto.getGenderType(),
                                dto.getAddress(),
                                10,
                                dto.getEmail()
                        )
                );
            }
            tblPatient.setItems(tmList);
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }
}

