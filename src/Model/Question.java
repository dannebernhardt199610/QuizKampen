package Model;

import java.io.Serializable;

public class Question implements Serializable {

    static final long serialVersionUID = 1L;

    String question;
    String[] answers;
    String correctAnswer;
    String genre;

    public Question(String question, String[] answers, String genre){

        this.question = question;
        this.answers = answers;
        this.correctAnswer = answers[0];
        this.genre = genre;

        //Blanda svarsalternativ
        shuffleAnswers();
    }

    public String toString(){

        String questionAndAlternatives = this.getQuestion() + "\n";

        for (int i = 0; i < 4; i++) {

            questionAndAlternatives += this.getAnswers()[i] + "\n";
        }

        return questionAndAlternatives;

    }

    public void shuffleAnswers(){

        String[] shuffled = this.getAnswers();

        String tempAnswer;

        for (int i = 0; i < 100; i++) {

            byte randomOne = (byte)(Math.random()*3);

            //System.out.println(randomOne);

            tempAnswer = shuffled[randomOne];

            byte randomTwo = (byte)(Math.round(Math.random()*3));

            //System.out.println(randomTwo);

            shuffled[randomOne] = shuffled[randomTwo];

            shuffled[randomTwo] = tempAnswer;

        }

        this.setAnswers(shuffled);
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() { return correctAnswer; }

    public String getGenre() { return genre; }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }



}