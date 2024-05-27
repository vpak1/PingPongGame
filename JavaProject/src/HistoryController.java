
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for the History FXML view.
 * 
 * @author pakv
 * 
 * Brief Description: manages user interactions and controls the behavior of the History view.
 */

public class HistoryController  implements Initializable{

    // Adding FXML components

    @FXML
    private Label title;
    @FXML
    private Button back;
    @FXML
    private Label history; 
    
    /**
     * Handles the action when the back button is pressed.
     * Closes the current stage.
     *
     * @param event The ActionEvent triggered by pressing the back button.
     */
    @FXML
    void goBack(ActionEvent event) {
        // Closing stage
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action when the showFull button is pressed.
     * Loads the FXMLFullHistory view and displays the full game history.
     *
     * @param event The ActionEvent triggered by pressing the showFull button.
     */

    @FXML
    void showFull(ActionEvent event) {
        try{
            // Load FXML file for the full history view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFullHistory.fxml"));
            Parent root = loader.load();
            // Create a new stage for the full history view
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            // Set the title and scene for the full history stage
            stage.setTitle("Full History");
            stage.setScene(scene);
            // Show the full history stage
            stage.show();
             // Read the game records from the file and set the history 
            Scanner scanner = new Scanner(new File("gameRecords.txt"));
            String fullHistory = "";
            while(scanner.hasNext()){
                fullHistory+=scanner.nextLine()+"\n";
            }
            //Closing scanner
            scanner.close();
              // Access the controller of the full history view in order to access setFullHistory() method
            FullHistoryController controller = loader.getController();
            // Parsing String fullHistory
            controller.setFullHistory(fullHistory);
        }
        catch(IOException e){  
            // Display an error
            System.out.println("Error loading FXML file: " + e.getMessage());}

    }


        @FXML 
        public void setHistory(String historyRecord){
            history.setText(historyRecord);
        }



    

     /**
     * Initializes controller
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
