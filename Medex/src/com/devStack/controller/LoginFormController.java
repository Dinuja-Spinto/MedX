package com.devStack.controller;

import com.devStack.db.DBConnection;
import com.devStack.enums.AccType;
import com.devStack.utill.CrudUtil;
import com.devStack.utill.PasswordConfig;
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
import java.sql.*;

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

        try{
            ResultSet rs = CrudUtil.execute("SELECT * FROM user WHERE email=? AND account_type=?",
                    email, accType.name());
            if(rs.next()){
                if(new PasswordConfig().decrypt(password,rs.getString("Password"))){
                    if(accType.equals(AccType.DOCTOR)){
                        setUi("DoctorDashboard");
                    }else{
                        setUi("PatientDashboardForm");
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING, "Your Password is incorrect!").show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, String.format("We can't find an (%s) Email", email)).show();
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
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
