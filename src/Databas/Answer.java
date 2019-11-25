package Databas;

import java.io.Serializable;

public class Answer implements Serializable {

    public static long serialVersionUID = 1L;

    String claim;
    boolean isCorrect;

    public Answer(String claim){

        this.claim = claim;
        this.isCorrect = false;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public boolean getisCorrect() {
        return isCorrect;
    }

    public void setisCorrect(boolean correct) {
        isCorrect = correct;
    }
}
