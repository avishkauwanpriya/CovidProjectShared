package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UserTM;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ManageUsersController {
    public AnchorPane mngUserWindow;
    public JFXTextField txtName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEMail;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;

    public JFXComboBox<String> cmbUserRole;
    public JFXButton btnSave;
    public JFXButton btnNewUser;
    public TableView<UserTM> tblUser;
    public JFXComboBox cmbHospital;
    public JFXComboBox cmbQrCenter;
    public ImageView imgShow_Password;
    public ImageView imgHide_Password;
    public Button remove=null;



    public void initialize() {

        setInitialize();
       
    }


    public void btnHome_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
            Scene newScene = new Scene(root);
            Stage existingStage = (Stage) mngUserWindow.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("COVID 19 Surveillance System");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if (checkForEmptyFields() == false) {
            new Alert(Alert.AlertType.ERROR, "Empty Fields", ButtonType.OK).show();
            return;
        } else if (validateContactNumbers(txtContactNumber.getText()) == false) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact No", ButtonType.OK).show();
            return;


        } else if (checkForDuplictateUserNames(txtUserName.getText()) == false) {
            new Alert(Alert.AlertType.ERROR, "UserName Exist", ButtonType.OK).show();
            return;

        }
        else  if(validateEmail(txtEMail.getText())==false){
            new Alert(Alert.AlertType.ERROR,"Invalid Email",ButtonType.OK).show();
            return;
        }


        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user values(?,?,?,?,?,?)");
            preparedStatement.setObject(1, txtName.getText());
            preparedStatement.setObject(2, txtContactNumber.getText());
            preparedStatement.setObject(3, txtEMail.getText());
            preparedStatement.setObject(4, txtUserName.getText());
            preparedStatement.setObject(5, txtPassword.getText());
            preparedStatement.setObject(6, cmbUserRole.getSelectionModel().getSelectedItem());

            preparedStatement.executeUpdate();

            if (cmbHospital.getSelectionModel().getSelectedItem() == null) {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO user_qrcenter VALUES(?,?)");
                preparedStatement1.setObject(1, txtUserName.getText());
                preparedStatement1.setObject(2, cmbQrCenter.getSelectionModel().getSelectedItem());

                preparedStatement1.executeUpdate();

            } else {
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO user_hospital VALUES(?,?)");
                preparedStatement1.setObject(1, txtUserName.getText());
                preparedStatement1.setObject(2, cmbHospital.getSelectionModel().getSelectedItem());

                preparedStatement1.executeUpdate();


            }

            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        remove = new Button("Remove");


        ObservableList<UserTM> users = tblUser.getItems();
        String selectedRole = (String) cmbUserRole.getSelectionModel().getSelectedItem();
        users.add(new UserTM(txtUserName.getText(), txtName.getText(), selectedRole, remove));
        tblUser.refresh();
        btnSave.setDisable(true);


    }



    public void btnNewUser_OnAction(ActionEvent actionEvent) {
        setNewUserOnClick();
    }

    private void setInitialize() {

        ArrayList<UserTM> userTableArrayList = new ArrayList<>();



        tblUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("role"));
        tblUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("remove"));



        btnSave.setDisable(true);
        txtName.setDisable(true);
        txtEMail.setDisable(true);
        txtContactNumber.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        cmbUserRole.setDisable(true);
        cmbHospital.setDisable(true);
        cmbQrCenter.setDisable(true);
        ObservableList list = FXCollections.observableArrayList("Admin", "PSTF", "Hospital IT", "Quarantine Centre IT");
        cmbUserRole.setItems(list);


        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username,name,role FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userTableArrayList.add(new UserTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),remove=new Button("Remove")));
            }
            ObservableList<UserTM> userTableObservableList = FXCollections.observableArrayList(userTableArrayList);
            tblUser.setItems(userTableObservableList);
            tblUser.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }






    }

    private void setNewUserOnClick() {
        txtName.clear();
        txtEMail.clear();
        txtContactNumber.clear();
        txtPassword.clear();
        txtUserName.clear();
        cmbUserRole.getSelectionModel().clearSelection();
        cmbQrCenter.getSelectionModel().clearSelection();
        cmbHospital.getSelectionModel().clearSelection();

        cmbQrCenter.setDisable(false);
        cmbHospital.setDisable(false);
        btnSave.setDisable(false);
        txtName.setDisable(false);
        txtEMail.setDisable(false);
        txtContactNumber.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        cmbUserRole.setDisable(false);

        setQRCenterIds();
        setHospitalIds();


    }

    private void setQRCenterIds() {

        ArrayList<String> qrIds = new ArrayList<>();

        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Id FROM quarantinecenter");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                qrIds.add(resultSet.getString(1));


            }
            ObservableList<String> qrIdsObservableList = FXCollections.observableArrayList(qrIds);
            if (qrIds.size() != 0) {
                cmbQrCenter.setItems(qrIdsObservableList);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setHospitalIds() {
        ArrayList<String> hospitalIds = new ArrayList<>();

        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT hospitalId FROM hospital");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hospitalIds.add(resultSet.getString(1));
            }
            ObservableList<String> hospitalIdsObservableList = FXCollections.observableArrayList(hospitalIds);
            cmbHospital.setItems(hospitalIdsObservableList);

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    private boolean checkForEmptyFields() {
        boolean flag = true;
        if (txtUserName.getText().trim().length() == 0 || txtPassword.getText().trim().length() == 0 || txtContactNumber.getText().trim().length() == 0 || txtEMail.getText().trim().length() == 0 || txtName.getText().trim().length() == 0 || (cmbHospital.getSelectionModel().getSelectedItem() == null && cmbQrCenter.getSelectionModel().getSelectedItem() == null) || cmbUserRole.getSelectionModel().getSelectedItem() == null) {
            flag = false;
        }


        return flag;
    }

    private boolean validateContactNumbers(String str) {
        boolean flag = true;
        /*String directorContactNo = txtDirectorContactNo.getText();
        String hospitalContactNo1= txtHospitalContactNo1.getText();
        String hospitalContactNo2 = txtHospitalContactNo2.getText();
        */
        if (str.length() != 11) {
            flag = false;
        } else {

            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i)) == false) {
                    if (i == 3) {
                        if (str.charAt(i) == '-') {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                }

            }
        }

        return flag;
    }

    private boolean checkForDuplictateUserNames(String str) {
        boolean flag = true;
        ArrayList<String> userNames = new ArrayList<>();
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userNames.add(resultSet.getString(1));
            }
            for (String userName : userNames) {
                if (str.equals(userName)) {
                    flag = false;
                }

            }


            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }
    private boolean validateEmail(String email){
        String mailFormat="^[A-Za-z0-9+_.-]+@(.+)$";
        String mail=email;
        boolean matches=mail.matches(mailFormat);

        return matches;
    }



}



