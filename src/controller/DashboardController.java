package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    public ImageView imgGlobe;
    public AnchorPane dashboard;

    public void imgGlobe_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root=FXMLLoader.load(this.getClass().getResource("/view/GlobalCOVID.fxml"));
            Scene newScene=new Scene(root);
            Stage existingStage=(Stage)dashboard.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("Manage Hospitals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imghospital_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root=FXMLLoader.load(this.getClass().getResource("/view/ManageHospital.fxml"));
            Scene newScene=new Scene(root);
            Stage existingStage=(Stage)dashboard.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("Manage Hospitals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imgQRCenter_OnClick(MouseEvent mouseEvent) {
        try {
           Parent root= FXMLLoader.load(this.getClass().getResource("/view/ManageQuarantineCenters.fxml"));
           Scene newScene=new Scene(root);
            Stage existingStage=(Stage)dashboard.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("Manage Quarantine Centres");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imgManageUsers_OnClick(MouseEvent mouseEvent) {
        try {
           Parent root= FXMLLoader.load(this.getClass().getResource("/view/ManageUsers.fxml"));
           Scene newScene=new Scene(root);
            Stage existingStage=(Stage)dashboard.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("Manage Users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
