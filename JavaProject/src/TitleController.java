import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller class for the Title FXML view.
 * 
 * @author pakv
 * 
 * Brief Description: manages user interactions and controls the behavior of the Title view.
 */


public class TitleController implements Initializable{

    // Adding FXML components

    @FXML
    private Label title;

    @FXML
    private Label player1;

    @FXML
    private Label player2;

    @FXML
    private TextField playerName1;
    
    @FXML
    private TextField playerName2;

    @FXML
    private Button start;
    
    @FXML
    private Button history;
    
    @FXML
    private Button exit;
    /**
    * Handles the action when the start button is pressed.
    * Loads the FXML file for the game view, sets player nicknames,
    * and displays the game view in a new stage.
    *
    * @param event The ActionEvent triggered by pressing the start button.
    */
     @FXML
    void start(ActionEvent event) {
        try {
            // Load FXML file for the history view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGame.fxml"));
            Parent root = loader.load();
             // Create a new stage for the history view
            GameController gameController = loader.getController();
            gameController.setNickname(playerName1.getText(), playerName2.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            // Set the title and scene for the history stage
            stage.setTitle("P I N G P O N G");
            stage.setScene(scene);
              // Show the history page
            stage.show();
        } catch (IOException e) {
            // Display an error
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    /**
     * Handles the action when the history button is pressed.
     * Loads the FXMLHistory view and passes player names to it.
     *
     * @param event The ActionEvent triggered by pressing the history button.
     */

    @FXML
    void processHistory(ActionEvent event) {
        try {
            // Load FXML file for the history view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHistory.fxml"));
            Parent root = loader.load();
             // Create a new stage for the history view
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Scanner scanner = new Scanner(new File("localSession.txt"));
            String history = "";
            while(scanner.hasNext()){
                history+=scanner.nextLine()+"\n";
            }
            //Closing scanner
            scanner.close();
            // Access the controller of the history view in order to access setHistory() method
            HistoryController cnt = loader.getController();
            cnt.setHistory(history);
            // Set the title and scene for the history stage
            stage.setTitle("History");
            stage.setScene(scene);
              // Show the history page
            stage.show();
        } catch (IOException e) {
            // Display an error
            System.err.println(e.getMessage());
        }
    }

     /**
     * Handles the action when the exit button is pressed.
     * Displays a confirmation alert for exiting the application.
     *
     * @param event The ActionEvent triggered by pressing the exit button.
     */

    @FXML
    private void askExitConfirmation(ActionEvent event) {
        // Display a confirmation alert for exiting the application
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Setting parametres for alert, such as title, header and content
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Do you really want to leave?");
        alert.setContentText("Press OK, if you want to exit");
        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        // Exit the application if the user clicks OK
        if (result.get() == ButtonType.OK) {System.exit(0);}
        else if (result.get() == ButtonType.CANCEL) { }
    }
     /**
     * Initializes controller
     **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
