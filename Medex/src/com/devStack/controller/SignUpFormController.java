package com.devStack.controller;

import com.devStack.db.DBConnection;
import com.devStack.dto.User;
import com.devStack.enums.AccType;
import com.devStack.utill.CrudUtil;
import com.devStack.utill.IdGenarator;
import com.devStack.utill.PasswordConfig;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {

    public JFXTextField fName;
    public JFXTextField lName;
    public JFXTextField emailTxt;
    public JFXRadioButton rBtnDoc;
    public AnchorPane regPageID;
    public JFXPasswordField passWordTxt;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        User user = new User(
                fName.getText(), lName.getText(), emailTxt.getText().toLowerCase(), new PasswordConfig().encrypt(passWordTxt.getText()), rBtnDoc.isSelected() ? AccType.DOCTOR : AccType.PATIENT
        );
        try{
            Boolean isSaved = CrudUtil.execute("INSERT INTO user VALUES (?,?,?,?,?,?)",new IdGenarator().genId(),user.getFirstName(),user.getLastName(),
                    user.getEmail(),user.getPassword(),user.getAccType().name());
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
                setUi();
            }else{
                new Alert(Alert.AlertType.WARNING,"Not Saved!").show();
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void haveAccountOnAction(ActionEvent actionEvent) throws IOException {
        this.setUi();
    }

    private void setUi() throws IOException {
        Stage stage = (Stage) regPageID.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.centerOnScreen();
    }
}
