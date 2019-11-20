package Model;

public class Player {
    private String name;
    private int score;
    private boolean isReady;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(boolean isCorrectAnswer) {
        if(isCorrectAnswer){
            this.score++;
        }
    }
}