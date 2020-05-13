package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.GlobalData;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


public class GlobalCOVIDController {

    public AnchorPane globalCovidWindow;

    public Label lblLastUpdate;
    public Label lblConfirmedCases;
    public Label lblRecoveredCases;
    public Label lblDeaths;
    public JFXDatePicker txtDate;
    public JFXTextField txtConfirmedCases;
    public JFXTextField txtRecoveredCases;
    public JFXTextField txtDeaths;


    public void initialize(){






        int confirmedCases=0;
        int recoveredCases=0;
        int deaths=0;
        String date="";


        try {
            Connection connection= DBConnection.getDBConnection().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM lastupdated");
            ResultSet resultSet = preparedStatement.executeQuery();



                while (resultSet.next()) {

                    confirmedCases = resultSet.getInt(1);
                    recoveredCases = resultSet.getInt(2);
                    deaths = resultSet.getInt(3);
                    date = resultSet.getString(4);




                }

                lblConfirmedCases.setText(confirmedCases+"");
                lblRecoveredCases.setText(recoveredCases+"");
                lblDeaths.setText(deaths+"");
                lblLastUpdate.setText(date);








        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void btnHome_OnClick(MouseEvent mouseEvent) {
        try {
            Parent root= FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
            Scene newScene=new Scene(root);
            Stage existingStage=(Stage)globalCovidWindow.getScene().getWindow();
            existingStage.setScene(newScene);
            existingStage.setTitle("COVID 19 Surveillance System");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void btnSave_OnAction(ActionEvent actionEvent) {




        if (validateGlobalDetails() == false ) {

            new Alert(Alert.AlertType.ERROR, "Invalid Inputs Or Fields Are Empty", ButtonType.OK).show();
            return;
        }
        else if (validateTimePeriod()==false){

            new Alert(Alert.AlertType.ERROR, "Invalid Time Period", ButtonType.OK).show();
            return;



        }
            else {
            LocalDate date = txtDate.getValue();
            boolean flag = true;

            try {
                Connection connection1 = DBConnection.getDBConnection().getConnection();
                PreparedStatement preparedStatement = connection1.prepareStatement("select * from  globalcoviddata");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getString(4).equals(txtDate.getValue().toString())) {




                        PreparedStatement preparedStatement2 = connection1.prepareStatement("UPDATE globalcoviddata set confirmedCases=(?),recoveredCases=(?),deaths=(?) WHERE Date=(?) ");
                        preparedStatement2.setObject(1,txtConfirmedCases.getText());
                        preparedStatement2.setObject(2,txtRecoveredCases.getText());
                        preparedStatement2.setObject(3,txtDeaths.getText());
                        preparedStatement2.setObject(4,date.toString());
                        preparedStatement2.executeUpdate();

                        preparedStatement2=connection1.prepareStatement("INSERT INTO lastupdated VALUES (?,?,?,?)");
                        preparedStatement2.setInt(1, Integer.parseInt(txtConfirmedCases.getText()));
                        preparedStatement2.setInt(2, Integer.parseInt(txtRecoveredCases.getText()));
                        preparedStatement2.setInt(3, Integer.parseInt(txtDeaths.getText()));
                        preparedStatement2.setString(4, (date + ""));
                        preparedStatement2.executeUpdate();





                        flag = false;



                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (flag==true) {
                try {
                    Connection connection2 = DBConnection.getDBConnection().getConnection();
                    PreparedStatement preparedStatement = connection2.prepareStatement("INSERT INTO globalcoviddata VALUES (?,?,?,?)");

                    preparedStatement.setInt(1, Integer.parseInt(txtConfirmedCases.getText()));
                    preparedStatement.setInt(2, Integer.parseInt(txtRecoveredCases.getText()));
                    preparedStatement.setInt(3, Integer.parseInt(txtDeaths.getText()));
                    preparedStatement.setString(4, (date + ""));
                    preparedStatement.executeUpdate();

                    preparedStatement=connection2.prepareStatement("INSERT INTO lastupdated VALUES (?,?,?,?)");
                    preparedStatement.setInt(1, Integer.parseInt(txtConfirmedCases.getText()));
                    preparedStatement.setInt(2, Integer.parseInt(txtRecoveredCases.getText()));
                    preparedStatement.setInt(3, Integer.parseInt(txtDeaths.getText()));
                    preparedStatement.setString(4, (date + ""));
                    preparedStatement.executeUpdate();



                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
            }










    private boolean validateGlobalDetails(){
        boolean flag=false;


        if((txtDate.getValue()==null||txtConfirmedCases.getText().trim().length()==0||txtRecoveredCases.getText().trim().length()==0||txtDeaths.getText().trim().length()==0)
        ||(validateForDigit(txtConfirmedCases.getText())&&validateForDigit(txtRecoveredCases.getText())&&validateForDigit(txtDeaths.getText()))==false){
            flag=false;

        }
        else{

            flag=true;
        }



    return flag;
    }
    private  boolean validateTimePeriod(){
        boolean flag;
        int year = txtDate.getValue().getYear();
        int month = txtDate.getValue().getMonthValue();

        if(year>2018){
            if(year==2019){
                if ((year+month)<2030){
                    flag=false;
                }
                else {
                    flag=true;
                }
            }
            else {
                flag=true;


            }



        }
        else {
            flag=false;
        }

        return flag;


    }
    private boolean validateForDigit(String str){
        boolean flag=false;
        for(int i=0;i<str.trim().length();i++){
            if(Character.isDigit(str.charAt(i))==false){
                flag=false;
                return flag;

            }
            else{
                flag=true;
            }



        }
        return flag;


    }
}
