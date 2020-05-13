package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageUsersController {
    public AnchorPane mngUserWindow;
    public JFXTextField txtName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEMail;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXComboBox cmbUserRole;
    public JFXButton btnSave;
    public JFXButton btnNewUser;
    public TableView tblUser;

    public void initialize(){
        setInitialize();
    }

    public void btnHome_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root= FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
            Scene newScene=new Scene(root);
            Stage existingStage=(Stage)mngUserWindow.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("COVID 19 Surveillance System");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnNewUser_OnAction(ActionEvent actionEvent) {
        setNewUserOnClick();
    }

    private void setInitialize(){
        btnSave.setDisable(true);
        txtName.setDisable(true);
        txtEMail.setDisable(true);
        txtContactNumber.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        cmbUserRole.setDisable(true);
        ObservableList list= FXCollections.observableArrayList("Admin","PSTF","Hospital IT","Quarantine Centre IT");
        cmbUserRole.setItems(list);



    }
    private void setNewUserOnClick(){
        btnSave.setDisable(false);
        txtName.setDisable(false);
        txtEMail.setDisable(false);
        txtContactNumber.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        cmbUserRole.setDisable(false);



    }


}
