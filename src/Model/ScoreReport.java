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
    private int currentRoundIndex;


    public ScoreReport(int numberOfRounds) {
        this.roundScores = new int[numberOfRounds];
    }

    public ScoreReport(int totalScore,  int[] roundScores) {
        this.totalScore = totalScore;
        this.roundScores = roundScores;
    }

    public synchronized void addPointToCurrentRound(){
        roundScores[currentRoundIndex]++;
        totalScore++;
    }


    public synchronized int getTotalScore() {
        return this.totalScore;
    }

    public synchronized void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public synchronized int[] getRoundScores() {
        return this.roundScores;
    }

    public synchronized void setRoundScores(int[] roundScores) {
        this.roundScores = roundScores;
    }

    public synchronized String getPlayerName() {
        return playerName;
    }

    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }

    public void setCurrentRoundIndex(int currentRoundIndex) {
        this.currentRoundIndex = currentRoundIndex;
    }

    @Override
    public synchronized String toString() {
        StringBuilder scoreReportText = new StringBuilder("Spelare: ");
        scoreReportText.append(this.playerName).append("\t").append("Totalpoäng: ").append(this.totalScore).append("\n");
        for (int i = 0; i < this.currentRoundIndex; i++) {
            System.out.println("Round index " + i);
            scoreReportText.append("Runda ").append(i + 1).append(": ").append(roundScores[i]).append("\t");
        }
        scoreReportText.append("\n\n");



        return scoreReportText.toString();
    }
}
