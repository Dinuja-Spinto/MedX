package com.devStack.controller;


import com.devStack.dto.DoctorDto;
import com.devStack.dto.User;
import com.devStack.enums.GenderType;
import com.devStack.utill.Cookie;
import com.devStack.utill.CrudUtil;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorRegistrationFormController {
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtNic;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtSpecializations;
    public TextArea txtAddress;
    public JFXRadioButton rBtnMale;
    public AnchorPane doctorRegistrationContext;
    public JFXButton btnSubmit;

    public void initialize() {
        loadUserData();
    }

    private void loadUserData() {
        User selectedUser = Cookie.selectedUser;
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtEmail.setText(selectedUser.getEmail());
    }

    private String generateDoctorId() throws SQLException, ClassNotFoundException {
        ResultSet result =
                CrudUtil.execute("SELECT doctor_id FROM doctor ORDER BY" +
                        " doctor_id DESC LIMIT 1"); // if the primary key is a string don't use this method
        // unsigned, cast, subscribe
        if (result.next()) {
            String selectedId = result.getString(1); // D-1**
            String[] splitData = selectedId.split("-"); // string tokenizer, String format
            String splitId = splitData[1];
            int id = Integer.parseInt(splitId); // unboxing
            id++;
            return "D-" + id;
        }
        return "D-1";
    }

    public void submitDataOnAction(ActionEvent actionEvent) {
        try {
            String docId = generateDoctorId();
            boolean isSaved = CrudUtil.execute("INSERT INTO doctor VALUES(?,?,?,?,?,?,?,?)",
                    docId,
                    txtFirstName.getText(),txtLastName.getText(),
                    txtContact.getText(),txtEmail.getText(),
                    txtSpecializations.getText(),
                    txtAddress.getText(),
                    rBtnMale.isSelected()?GenderType.MALE.name():GenderType.FE_MALE.name()
            );
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Welcome Doctor...").show();
                setUi("DoctorDashboard");
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) doctorRegistrationContext.getScene().getWindow();
        System.out.println(stage);
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
