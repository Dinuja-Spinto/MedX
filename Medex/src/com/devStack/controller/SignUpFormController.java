package com.devStack.controller;

import com.devStack.dto.User;
import com.devStack.enums.AccType;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medex",
                    "root",
                    "Dinuj@5615011"
            );
            String sql = "INSERT INTO user VALUES (?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1,new IdGenarator().genId());
            pstm.setString(2,user.getFirstName());
            pstm.setString(3,user.getLastName());
            pstm.setString(4,user.getEmail());
            pstm.setString(5,user.getPassword());
            pstm.setString(6,user.getAccType().name());
            int isSaved = pstm.executeUpdate();
            if (isSaved>0){
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
