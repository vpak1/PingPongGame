import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for the JavaFX application.
 * This class is responsible for launching the application and setting up the initial stage.
 * 
 * @author pakv
 * 
 * Brief Description: This class loads the FXML file for the title page and sets up the
 * initial stage for the JavaFX application.
 */


public class App extends Application {
    /**
     * The main method to launch the JavaFX application.
     * 
     * @param args The command-line arguments.
     */

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Override the start method to set up the initial stage.
     * 
     * @param stage The primary stage for the application.
     * @throws Exception If there is an issue loading the FXML file.
     */
   @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file for the title page
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTitle.fxml"));
        // Creating a scene with the loaded FXML file
        Scene scene = new Scene(root);
        //Setting up the scene and title for the stage
        stage.setTitle("Title Page");
        stage.setScene(scene);
        //Display stage
        stage.show();
    }
}
