package Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Question  implements Serializable {

    static final long serialVersionUID = 1L;

    //First index in the answers array will always be the correct answer

    String question;
    String[] answers;
    String correctAnswer;


    public Question(String question, String[] answers) {
        this.question = question;
        this.answers = answers;

        if(answers.length != 4)
            throw new IllegalArgumentException("There must be 4 answers in the array!");

        this.correctAnswer = answers[0];
        this.answers = getShuffledStringArray(answers);
    }

    private String[] getShuffledStringArray(String[] answers) {
        List<String> answersList = Arrays.asList(answers);
        Collections.shuffle(answersList);
        answersList.toArray(answers);
        return answers;
    }

    public Question(String question, String correctAnswer, String answer2, String answer3, String answer4) {
        this.question = question;
        this.answers = new String[]{correctAnswer, answer2, answer3, answer4};

        this.correctAnswer = answers[0];
        this.answers = getShuffledStringArray(answers);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
