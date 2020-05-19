package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageQuarantineCentersController {
    public AnchorPane mngQuarantineCentreWindow;
    public JFXTextField txtId;
    public JFXTextField txtCity;
    public JFXTextField txtHeadContactNo;
    public JFXTextField txtCentreContactNo2;
    public JFXTextField txtQuarantineCentreName;
    public JFXTextField txtHead;
    public JFXTextField txtCentreContactNo1;
    public JFXTextField txtCapacity;
    public JFXComboBox cmbDistrict;

    public JFXTextField txtSearchQuarantineCentre;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnNewUser;
    public JFXListView lstQuarantineCenter;

    public void initialize(){
        setInitialize();
        ArrayList<String> quarantineCenters=new ArrayList<>();
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT centerName FROM quarantinecenter");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                quarantineCenters.add(resultSet.getString(1));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        txtSearchQuarantineCentre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstQuarantineCenter.getItems().clear();
                for (String center:quarantineCenters) {
                    if(center.contains(newValue)){
                        lstQuarantineCenter.getItems().add(center);
                        lstQuarantineCenter.refresh();
                    }

                }



            }
        });
    }

    public void btnHome_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root= FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
            Scene newScene=new Scene(root);
            Stage existingStage=(Stage)mngQuarantineCentreWindow.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("COVID 19 Surveillance System");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(checkForEmptyFields()==false){
            new Alert(Alert.AlertType.ERROR,"Empty Fields", ButtonType.OK).show();
            return;

        }
        else if(validateCapacity()==false){
            new Alert(Alert.AlertType.ERROR,"Invalid Capacity", ButtonType.OK).show();
            return;

        }
        else if(validateContactNumbers(txtHeadContactNo.getText())==false||validateContactNumbers(txtCentreContactNo1.getText())==false||validateContactNumbers(txtCentreContactNo2.getText())==false){
            new Alert(Alert.AlertType.ERROR,"Invalid ContactNo", ButtonType.OK).show();
            return;


        }








        try {


            Connection connection= DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO quarantinecenter VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,txtId.getText());
            preparedStatement.setObject(2,txtQuarantineCentreName.getText());
            preparedStatement.setObject(3,txtCity.getText());
            preparedStatement.setObject(4,cmbDistrict.getSelectionModel().getSelectedItem());
            preparedStatement.setObject(5,txtHead.getText());
            preparedStatement.setObject(6,txtHeadContactNo.getText());
            preparedStatement.setObject(7,txtCentreContactNo1.getText());
            preparedStatement.setObject(8,txtCentreContactNo2.getText());
            preparedStatement.setObject(9,txtCapacity.getText());




            preparedStatement.executeUpdate();

            connection.close();



        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnSave.setDisable(true);



    }


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        setBtnAddNewOnClick();
        setId();
    }
    private void setInitialize(){
        ObservableList list= FXCollections.observableArrayList("Colombo","Ampara","Anuradhapura","Badulla","Batticaloa","Colombo","Galle","Gampaha","Hambantota","Jaffna","Kalutara","Kandy","Kegalle","Kilinochchi","Kurunegala","Mannar","Matale","Matara","Moneragala","Mullaitivu","Nuwara Eliya","Polonnaruwa","Puttalam","Ratnapura","Trincomalee","Vavuniya");
        cmbDistrict.setItems(list);

        btnSave.setDisable(true);
        txtId.setDisable(true);
        txtQuarantineCentreName.setDisable(true);
        txtCentreContactNo1.setDisable(true);
        txtCentreContactNo2.setDisable(true);
        txtCity.setDisable(true);
        txtCapacity.setDisable(true);
        txtHead.setDisable(true);
        txtHeadContactNo.setDisable(true);
        cmbDistrict.setDisable(true);



    }

    private void setBtnAddNewOnClick(){

        txtId.clear();
        txtQuarantineCentreName.clear();
        txtCentreContactNo1.clear();
        txtCentreContactNo2.clear();
        txtCity.clear();
        txtCapacity.clear();
        txtHead.clear();
        txtHeadContactNo.clear();
        cmbDistrict.getSelectionModel().clearSelection();


        btnSave.setDisable(false);
        txtId.setDisable(false);
        txtQuarantineCentreName.setDisable(false);
        txtCentreContactNo1.setDisable(false);
        txtCentreContactNo2.setDisable(false);
        txtCity.setDisable(false);
        txtCapacity.setDisable(false);
        txtHead.setDisable(false);
        txtHeadContactNo.setDisable(false);
        cmbDistrict.setDisable(false);

    }
    private void setId(){
        ArrayList<String> centerIds=new ArrayList<>();
        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quarantinecenter");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                centerIds.add(resultSet.getString(1));
            }
            if(centerIds.size()==0){
                txtId.setText("C001");
            }
            else {
                int lastIdNum = Integer.parseInt(centerIds.get(centerIds.size() - 1).substring(1, 4));
                if (lastIdNum < 9) {
                    txtId.setText("C00" + (lastIdNum + 1));
                } else if (lastIdNum < 99) {
                    txtId.setText("C0" + (lastIdNum + 1));

                } else {
                    txtId.setText("C" + (lastIdNum + 1));


                }
            }
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkForEmptyFields(){

        boolean flag=true;
        if(txtQuarantineCentreName.getText().trim().length()==0||txtCity.getText().trim().length()==0||txtCapacity.getText().trim().length()==0||txtCentreContactNo1.getText().trim().length()==0||txtCentreContactNo2.getText().trim().length()==0||txtHead.getText().trim().length()==0||txtHeadContactNo.getText().trim().length()==0||cmbDistrict.getSelectionModel().getSelectedItem()==null){
            flag=false;
        }


        return flag;
    }

    private boolean validateCapacity(){
        boolean flag=true;
        String capacity=txtCapacity.getText();
        for(int i=0;i<capacity.length();i++){
            if(Character.isDigit(capacity.charAt(i))==false){
                flag=false;
                break;
            }

        }
        return flag;



    }

    private boolean validateContactNumbers(String str){
        boolean flag=true;
        /*String directorContactNo = txtDirectorContactNo.getText();
        String hospitalContactNo1= txtHospitalContactNo1.getText();
        String hospitalContactNo2 = txtHospitalContactNo2.getText();
        */
        if(str.length()!=11){
            flag=false;
        }
        else {

            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i)) == false) {
                    if (i == 3) {
                        if(str.charAt(i)=='-'){
                            flag = true;}
                        else{
                            flag=false;
                        }
                    } else {
                        flag = false;
                    }
                }

            }
        }

        return flag;
    }

}
