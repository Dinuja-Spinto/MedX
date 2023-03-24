package com.devStack.controller;


import com.devStack.dto.DoctorDto;
import com.devStack.utill.Cookie;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class DoctorDashboardController {

    public AnchorPane DoctorDashContexr;
    public Label lblDate;
    public Label lblTime;

    public void initialize() throws IOException {
        //checkUser();
        initializeData();
       // checkDoctorData();
    }

    private void initializeData() throws IOException {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                            lblTime.setText(LocalTime.now().format(dtf));
                        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

   /* private void checkDoctorData() throws IOException {
        Optional<DoctorDto> selectedDoctor =
                Database.doctorTable.stream()
                        .filter(e -> e.getEmail().equals("shashika@gmail.com"))
                        .findFirst();
        if (!selectedDoctor.isPresent()){
            setUi("DoctorRegistrationForm");
        }
    }*/

    public void checkUser() throws IOException {
        if (null == Cookie.selectedUser){
            setUi("LoginForm");
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) DoctorDashContexr.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void navigateToPatientManagementPage(ActionEvent actionEvent) throws IOException {
        setUi("PatientManagement");
    }
}
