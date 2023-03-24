package com.devStack.controller;

import com.devStack.db.Database;
import com.devStack.dto.User;
import com.devStack.enums.AccType;
import com.devStack.utill.Cookie;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public AnchorPane logInPage;
    @FXML
    JFXTextField txtEmail;
    @FXML
    JFXTextField txtPassword;
    @FXML
    JFXRadioButton rBtnDoctor;

    public void signinOnAction(ActionEvent actionEvent) throws IOException{
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        AccType accType = rBtnDoctor.isSelected() ? AccType.DOCTOR : AccType.PATIENT;

        for (User dto : Database.userTable) {
            if (dto.getEmail().trim().toLowerCase().equals(email)) {
                if (dto.getPassword().equals(password)) {
                    if (dto.getAccType().equals(accType)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Success!").show();
                        Cookie.selectedUser=dto;
                        this.setUi("DoctorDashboard");
                        return;
                    } else {
                        new Alert(Alert.AlertType.WARNING, String.format("We can't find your %s Account", accType.name())).show();
                        return;
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Your Password is incorrect!").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.WARNING, String.format("We can't find an (%s) Email", email)).show();
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        this.setUi("SignUpForm");
    }

    private void setUi(String ui) throws IOException {
        Stage stage=(Stage) logInPage.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader. load(getClass().getResource("../view/"+ui+".fxml"))));
        stage.centerOnScreen();
    }
}
