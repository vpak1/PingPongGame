import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for the Game FXML view.
 * 
 * @author pakv
 * 
 * Brief Description: handles PingPong gaming process.
 */

public class GameController implements Initializable {
    // Adding FXML components
    @FXML
    private AnchorPane scene;
    @FXML
    private Label pName1;
    @FXML
    private Label pName2;
    @FXML
    private Label pScore1;
    @FXML
    private Label pScore2;
    @FXML
    private Rectangle pad1;
    @FXML
    private Rectangle pad2;
    @FXML
    private Circle ball;
    @FXML
    private Label gameOver;
    @FXML
    private Label playerWin;
    @FXML
    private Label instruction;
 
    // Game variables
    private Set<KeyCode> keysPressed = new HashSet<>();
    private double ballXSpeed = 8;
    private double ballYSpeed = 5;
    private double padSpeed = 100;

    private int playerScore1 = 0;
    private int playerScore2 = 0;

    private int playerWins1 = 0;
    private int playerWins2=0;
    private int playerLoses1=0;
    private int playerLoses2=0;

    private ArrayList<Integer> scores1=new ArrayList<Integer>();
    private ArrayList<Integer> scores2=new ArrayList<Integer>();

    private int pHScore1;
    private int pHScore2;

    private int offset = 250;

     // Timelines for game and game-over handling

    Timeline gameTimer = new Timeline(new KeyFrame(Duration.millis(30 * 1000), e -> handleGameOver()));
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            checkCollisionScene(scene);
            ball.setLayoutX(ball.getLayoutX() + ballXSpeed);
            ball.setLayoutY(ball.getLayoutY() + ballYSpeed);
        }
    }));

    /**
     * Set nicknames for players.
     *
     * @param nickname1 The nickname for player 1.
     * @param nickname2 The nickname for player 2.
     */

    @FXML
    public void setNickname(String nickname1, String nickname2) {
        //Setting player nicknames lables to received inputs from Title page
        pName1.setText(nickname1);
        pName2.setText(nickname2);
    }
    
    /**
     * Handles key presses during the game.
     * Moves paddles and performs actions based on key presses.
     *
     * @param event The KeyEvent triggered by a key press.
     */

    private void handleKeyPress(KeyEvent event) {

        keysPressed.add(event.getCode());

        // Check which keys are pressed and move paddles accordingly
        if (keysPressed.contains(KeyCode.W)) {
            movePad(pad1, -padSpeed);
        }
        if (keysPressed.contains(KeyCode.S)) {
            movePad(pad1, padSpeed);
        }
        if (keysPressed.contains(KeyCode.UP)) {
            movePad(pad2, -padSpeed);
        }
        if (keysPressed.contains(KeyCode.DOWN)) {
            movePad(pad2, padSpeed);
        }
        if (keysPressed.contains(KeyCode.R)) {
            retry();
        }
         // Close the game window and save history
        if(keysPressed.contains(KeyCode.X)){
            Stage stage = (Stage) scene.getScene().getWindow();
            stage.close();
            saveHistory();

        }
    }

    /**
     * Handles key releases during the game.
     * Removes the released key from the set of pressed keys.
     *
     * @param event The KeyEvent triggered by a key release.
     */

    private void handleKeyRelease(KeyEvent event) {
        keysPressed.remove(event.getCode());
    }

    /**
     * Moves the specified paddle by a certain delta value.
     * Ensures that the paddle stays within the scene boundaries.
     *
     * @param pad    The paddle to move.
     * @param deltaY The change in the paddle's Y-coordinate.
     */

    private void movePad(Rectangle pad, double deltaY) {
        double sceneHeight = pad.getScene().getHeight();
        double newPosY = pad.getHeight();
        newPosY = clamp(pad.getY() + deltaY, 0 - offset, (sceneHeight - newPosY) - offset);

        pad.setY(newPosY);

    }

    /**
     * Clamps a value within a specified range.
     *
     * @param value The value to be clamped.
     * @param min   The minimum allowed value.
     * @param max   The maximum allowed value.
     * @return The clamped value within the specified range.
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Checks for collisions with the scene boundaries and paddles.
     * Adjusts ball speed and updates scores accordingly.
     *
     * @param node The AnchorPane representing the game scene.
     */

    public void checkCollisionScene(AnchorPane node) {
        Bounds bounds = node.getBoundsInLocal();
        Bounds pad1Bounds = pad1.getBoundsInParent();
        Bounds pad2Bounds = pad2.getBoundsInParent();
        // Check if the ball has hit the right or left scene boundary
        boolean rightBorder = ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius());
        // Check if the ball has hit the top or bottom scene boundary
        boolean bottomBorder = ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius());
        boolean topBorder = ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius());
 
        // If the ball hits the right or left border, reverse the horizontal direction
        if (rightBorder || leftBorder) {
            ballXSpeed *= -1;
            handlePaddleScore(leftBorder);
        }
        // If the ball hits the top or bottom border, reverse the vertical direction
        if (bottomBorder || topBorder) {
            ballYSpeed *= -1;
        }

          // Check for collisions with paddles
        checkCollisionPaddle(pad1Bounds, pad1);
        checkCollisionPaddle(pad2Bounds, pad2);
    }

    /**
     * Handles scoring when the ball hits a paddle.
     *
     * @param leftBorder A boolean indicating whether the left border was hit.
     */

    public void handlePaddleScore(boolean leftBorder) {
        // If the left border was hit, the ball is on the right side; update player 2's score
        if (leftBorder) {
            playerScore2++;
            pScore2.setText("Score" + " : " + playerScore2);
        } else {
            // If the right border was hit, the ball is on the left side; update player 1's score
            playerScore1++;
            pScore1.setText("Score" + " : " + playerScore1);
        }
        // Reset the game state for a new round
        resetGame();
    }

    /**
     * Resets the game state for a new round.
     * The ball is placed in the center, and its speed is reset based on the direction of the previous round.
     */
    public void resetGame() {
        //placing ball in the center
        ball.setLayoutX(scene.getWidth() / 2);
        ball.setLayoutY(scene.getHeight() / 2);
        
        // Reset the horizontal speed of the ball based on the direction of the previous round
        if (ballXSpeed > 0){
            ballXSpeed = -8; // Move towards the left
        }else{
            ballXSpeed = 8; // Move towards the right
        }
        // Set the default vertical speed of the ball
        ballYSpeed = 5;
    }

    /**
    * Handles the collision between the ball and a paddle.
    * Adjusts the ball's trajectory based on its collision with the specified paddle.
     *
    * @param paddle The paddle involved in the collision.
    */

    public void handlePaddleCollision(Rectangle paddle) {
        // Calculate the center Y-coordinate of the ball
        double ballCenterY = ball.getLayoutY() + ball.getRadius();
        double paddleCenterY = paddle.getY() + paddle.getHeight() / 2.0;

         // Calculate the center Y-coordinate of the paddle
        double relativeIntersectY = (paddleCenterY - ballCenterY) / (paddle.getHeight() / 2.0);

        // Set the new ball speeds based on the relative intersect Y
        ballXSpeed *= -1; // Reverse the horizontal direction
        ballYSpeed = (int) (relativeIntersectY * 5);
    }

    /**
    * Checks for a collision between the ball and a paddle.
    * If a collision is detected, adjusts the ball's trajectory and updates scores accordingly.
    *
    * @param paddleBounds The bounds of the paddle.
    * @param paddle The paddle involved in the collision.
    
    */
    public void checkCollisionPaddle(Bounds paddleBounds, Rectangle paddle) {
        // Get the bounds of the ball
        Bounds ballBounds = ball.getBoundsInParent();
        // Variable to track if a collision is detected
        boolean collisionDetected = false;
        // Check if the bounds of the ball intersect with the bounds of the paddle
        if (ballBounds.intersects(paddleBounds)) {
            collisionDetected = true;
    
            // Calculate the ball's center Y
            double ballCenterY = ball.getLayoutY() + ball.getRadius();
    
            // Calculate the paddle's top and bottom Y boundaries
            double paddleTopY = paddle.getY();
            double paddleBottomY = paddle.getY() + paddle.getHeight();
    
            // Check if the ball hits the top edge of the paddle
            if (ballCenterY < paddleTopY) {
                // Handle collision with the top edge of the paddle
                ballYSpeed = -Math.abs(ballYSpeed); // This will send the ball upwards
            }
    
            // Check if the ball hits the bottom edge of the paddle
            if (ballCenterY > paddleBottomY) {
                // Handle collision with the bottom edge of the paddle
                ballYSpeed = Math.abs(ballYSpeed); // This will send the ball downwards
            }
    
            // Reverse the X direction of the ball to bounce it back into play
            ballXSpeed = -ballXSpeed * 1.1;
        }
    }

    /**
     * Handles the game over scenario by determining the winner, updating scores, and stopping timelines.
     * Displays appropriate messages and instructions for restarting.
     */

    public void handleGameOver() {
        // Check which player has a higher score to determine the winner
        if (playerScore1 > playerScore2) {
            playerWin.setText(pName1.getText()+" wins");
            playerWins1+=1;
            playerLoses2+=1;
        } else {
            playerWin.setText(pName2.getText()+" wins");
            playerWins2+=1;
            playerLoses2+=1;
        }
        // Stop the animation timelines
        timeline.stop();
        gameTimer.stop();
        
         // Display game over message and instructions
        gameOver.setText("Game Over");
        instruction.setText("To Restart Press R \n To Exit press X");
        
         // Calculate and display highest scores
        calculateHScore();
    }

    /**
     * Calculates the highest scores for both players based on their game session scores.
     * Updates the pHScore1 and pHScore2 variables accordingly.
     */

    public void calculateHScore(){
         // Calculate highest score for player 1
        scores1.add(playerScore1);
        if (scores1.isEmpty()) {
            throw new IllegalArgumentException("ArrayList is empty");
        }

        pHScore1 = scores1.get(0); // Assume the first score is the highest

        for (int score : scores1) {
            if (score > pHScore1) {
                pHScore1 = score; // Update maxScore if a higher score is found
            }
        }
        // Calculate highest score for player 2
        scores2.add(playerScore2);
        if (scores2.isEmpty()) {
            throw new IllegalArgumentException("ArrayList is empty");
        }

        pHScore2 = scores2.get(0); // Assume the first score is the highest

        for (int score : scores2) {
            if (score > pHScore2) {
                pHScore2 = score; // Update maxScore if a higher score is found
            }
        }
    }

    /**
    * Saves the game session history to a file. It calls the setHistoryFile method to store
    * overall game statistics and then clears the existing file before writing the current
    * session history.
    * Handles exceptions by printing the stack trace.
    */

    public void saveHistory(){
        try{
            // Create a new GameStatistics instance
            GameStatistics gameStatistics = new GameStatistics();
            // Set overall game statistics
            gameStatistics.setHistoryFile(pName1.getText(), playerWins1, playerLoses1, pHScore1, pName2.getText(), playerWins2, playerLoses2, pHScore2);
            // Clear the existing file
            gameStatistics.clearFile();
            // Set the session history in the file
            gameStatistics.setSessionHistoryFile(pName1.getText(), playerWins1, playerLoses1, pHScore1, pName2.getText(), playerWins2, playerLoses2, pHScore2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * Resets the game state to allow players to retry. It sets player scores back to zero,
    * updates the score labels, and displays the necessary labels for a new game session.
    * It also restarts the animation timelines.
    */
    public void retry(){
        playerScore1 = 0; 
        playerScore2 = 0;

        pScore1.setText("Score" + " : " + playerScore1);
        pScore2.setText("Score" + " : " + playerScore2);

        gameOver.setText(" ");
        playerWin.setText(" ");
        instruction.setText(" ");
        timeline.play();
        gameTimer.play();
    }
    
    /**
     * Initializes the game controller when the associated FXML file is loaded.
 * It sets up the initial state of the game, such as setting score labels, configuring
 * focus traversal, and starting animation timelines.
 *
 * @param location The URL location of the FXML file.
 * @param resources The ResourceBundle for the FXML file.
 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set initial score labels
        pScore1.setText("Score" + " : " + playerScore1);
        pScore2.setText("Score" + " : " + playerScore2);
        // Configure focus traversal for the scene and paddles
        scene.setFocusTraversable(true);
        pad1.setFocusTraversable(true);
        pad2.setFocusTraversable(true);
        // Set up key event handlers for paddles
        pad1.setOnKeyPressed(this::handleKeyPress);
        pad1.setOnKeyReleased(this::handleKeyRelease);

        pad2.setOnKeyPressed(this::handleKeyPress);
        pad2.setOnKeyReleased(this::handleKeyRelease);
        // Start animation timelines
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        gameTimer.play();
    }

}
