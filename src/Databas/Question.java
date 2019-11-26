package Databas;

import java.io.Serializable;

public class Question implements Serializable {

    public static long serialVersionUID = 1L;

    String question;
    Answer[] answers;
    String correctAnswer;

    public Question(String question, Answer[] answers){

        this.question = question;
        this.answers = answers;
        this.correctAnswer = this.answers[0].getClaim();
    }

    public String toString(){

        String questionAndAlternatives = this.getQuestion() + "\n";

        for (int i = 0; i < 4; i++) {

            questionAndAlternatives += this.getAnswers()[i].getClaim() + "\n";
        }

        return questionAndAlternatives;

    }

    public void shuffleAnswers(){

        Answer[] shuffled = this.getAnswers();

        Answer tempAnswer;

        for (int i = 0; i < 100; i++) {

            byte randomOne = (byte)(Math.random()*3);

            tempAnswer = shuffled[randomOne];

            byte randomTwo = (byte)(Math.random()*3);

            shuffled[randomOne] = shuffled[randomTwo];

            shuffled[randomTwo] = tempAnswer;

        }

        this.setAnswers(shuffled);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
}
