package Model;

import java.io.Serializable;

public class ScoreReport implements Serializable {

    static final long serialVersionUID = 1L;

    //This class holds an array with the scores for each round and an int for total score (for ONE player)
    //addPointsToCurrentRound() will be called if the player had the correct answer
    //After each round, goToNextRound() will be called

    String playerName;
    private int totalScore = 0;
    private int[] roundScores;


    public ScoreReport(int numberOfRounds) {
        this.roundScores = new int[numberOfRounds];
    }

    public void addPointsToCurrentRound(int currentRoundIndex){
        roundScores[currentRoundIndex]++;
        totalScore++;
    }


    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int[] getRoundScores() {
        return roundScores;
    }

    public void setRoundScores(int[] roundScores) {
        this.roundScores = roundScores;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
