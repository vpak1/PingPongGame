
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Handles game statistics and manages the game records file.
 * 
 * @author pakv
 * 
 * Brief Description: provides methods to update and store game-related information in a text file;
 * uses Java I/O classes for file handling.
 */

public class GameStatistics {

     /**
     * Appends game-related information to the game records file.
     *
     * @param pN1      Player 1's nickname.
     * @param pW1      Player 1's number of wins.
     * @param pL1      Player 1's number of losses.
     * @param pHScore1 Player 1's highest score.
     * @param pN2      Player 2's nickname.
     * @param pW2      Player 2's number of wins.
     * @param pL2      Player 2's number of losses.
     * @param pHScore2 Player 2's highest score.
     */

    public void setHistoryFile(String pN1, int pW1, int pL1, int pHScore1, String pN2, int pW2, int pL2, int pHScore2){
        try {
            //adding all game records to a gameRecords.txt file
            PrintWriter newWriter= new PrintWriter(new BufferedWriter(new FileWriter("gameRecords.txt", true)));
                newWriter.println("\n"+pN1);
                newWriter.println("Wins "+pW1);
                newWriter.println("Loses "+pL1);
                newWriter.println("Highest Score "+pHScore1);
                newWriter.println(pN2);
                newWriter.println("Wins "+pW2);
                newWriter.println("Loses "+pL2);
                newWriter.println("Highest Score "+pHScore2);
                newWriter.close();
        } catch (IOException e) {
             // Display an error
            e.printStackTrace();
        }
    }
     /**
     * Appends one session game-related information to the game records file.
     *
     * @param pN1      Player 1's nickname.
     * @param pW1      Player 1's number of wins.
     * @param pL1      Player 1's number of losses.
     * @param pHScore1 Player 1's highest score.
     * @param pN2      Player 2's nickname.
     * @param pW2      Player 2's number of wins.
     * @param pL2      Player 2's number of losses.
     * @param pHScore2 Player 2's highest score.
     */
    public void setSessionHistoryFile(String pN1, int pW1, int pL1, int pHScore1, String pN2, int pW2, int pL2, int pHScore2){
        try {
            //adding current game records to a gameRecords.txt file
            PrintWriter newWriter= new PrintWriter(new BufferedWriter(new FileWriter("localSession.txt")));
                newWriter.println("\n"+pN1);
                newWriter.println("Wins "+pW1);
                newWriter.println("Loses "+pL1);
                newWriter.println("Highest Score "+pHScore1);
                newWriter.println(pN2);
                newWriter.println("Wins "+pW2);
                newWriter.println("Loses "+pL2);
                newWriter.println("Highest Score "+pHScore2);
                newWriter.close();
        } catch (IOException e) {
             // Display an error
            e.printStackTrace();
        }
    }
     /**
     * Appends one session game-related information to the game records file.
     * Clears file after one session
     */
    public void clearFile(){
        try {
            PrintWriter writer = new PrintWriter("localSession.txt");
            writer.print("");
            writer.close();

        } catch (IOException e) {
             // Display an error
            e.printStackTrace();
        }
    }

}