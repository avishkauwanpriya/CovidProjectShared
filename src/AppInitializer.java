import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root=FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
        Scene dashboard=new Scene(root);
        primaryStage.setScene(dashboard);
        primaryStage.setTitle("COVID 19 Surveillance System");
        primaryStage.centerOnScreen();
        primaryStage.show();


    }
}
