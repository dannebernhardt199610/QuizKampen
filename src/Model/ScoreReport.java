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

    public ScoreReport(int totalScore,  int[] roundScores, int currentRoundIndex) {
        this.totalScore = totalScore;
        this.roundScores = roundScores;
        this.currentRoundIndex = currentRoundIndex;
    }

    public void addPointToCurrentRound(int currentRoundIndex){
        this.currentRoundIndex = currentRoundIndex;
        roundScores[currentRoundIndex]++;
        totalScore++;
    }


    public int getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int[] getRoundScores() {
        return this.roundScores;
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


    @Override
    public synchronized String toString() {
        StringBuilder scoreReportText = new StringBuilder("Spelare: ");
        scoreReportText.append(this.playerName).append("\t").append("Totalpoäng: ").append(this.totalScore).append("\n");
        for (int i = 0; i < this.currentRoundIndex + 1; i++) {
            System.out.println("Round index " + i);
            scoreReportText.append("Runda ").append(i + 1).append(": ").append(roundScores[i]).append("\t");
        }
        scoreReportText.append("\n\n");



        return scoreReportText.toString();
    }
}
