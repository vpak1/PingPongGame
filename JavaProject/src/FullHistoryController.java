import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for the FullHistory FXML view.
 * 
 * @author pakv
 * 
 * Brief Description: manages user interactions and controls the behavior of the FullHistory view.
 */

public class FullHistoryController implements Initializable{ 
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
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the full game history to be displayed.
     *
     * @param record The full game history to be displayed.
     */
    
    @FXML
    void setFullHistory(String record){
        history.setText(record);
    }

       /**
     * Initializes controller
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
