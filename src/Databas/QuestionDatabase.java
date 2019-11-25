package Databas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class QuestionDatabase implements Serializable {

    public static long serialVersionUID = 1L;


    private ArrayList[] genrer;
    private ArrayList<Question> movieQuestions;
    private ArrayList<Question> musicQuestions;
    private ArrayList<Question> gameQuestions;
    private ArrayList<Question> javaQuestions;

    public QuestionDatabase(){

        this.genrer = new ArrayList[4];

        this.movieQuestions = new ArrayList<>();
        this.musicQuestions = new ArrayList<>();
        this.gameQuestions = new ArrayList<>();
        this.javaQuestions = new ArrayList<>();

        this.genrer[0] = this.musicQuestions;
        this.genrer[1] = this.movieQuestions;
        this.genrer[2] = this.gameQuestions;
        this.genrer[3] = this.javaQuestions;

        Path[] paths = new Path[4];
        paths[0] = Paths.get("src\\Databas\\musicQuestions.txt");
        paths[1] = Paths.get("src\\Databas\\movieQuestions.txt");
        paths[2] = Paths.get("src\\Databas\\gameQuestions.txt");
        paths[3] = Paths.get("src\\Databas\\javaQuestions.txt");

        for (int i = 0; i < paths.length; i++) {

            try(BufferedReader br = Files.newBufferedReader(paths[i], Charset.forName("ISO-8859-1"))){

                String tempString;

                while((tempString = br.readLine()) != null){

                    String question = tempString;

                    Answer[] answers = new Answer[4];
                    for (int j = 0; j < 4; j++) {
                        answers[j] = new Answer(br.readLine());
                    }

                    Question questionObject = new Question(question,answers);

                    this.genrer[i].add(questionObject);

                }

            }catch(IOException io){
                io.printStackTrace();
            }
        }

    }

    public ArrayList<Question> getMovieQuestions() {
        return movieQuestions;
    }

    public void setMovieQuestions(ArrayList<Question> movieQuestions) {
        this.movieQuestions = movieQuestions;
    }

    public ArrayList<Question> getMusicQuestions() {
        return musicQuestions;
    }

    public void setMusicQuestions(ArrayList<Question> musicQuestions) {
        this.musicQuestions = musicQuestions;
    }

    public ArrayList<Question> getGameQuestions() {
        return gameQuestions;
    }

    public void setGameQuestions(ArrayList<Question> gameQuestions) {
        this.gameQuestions = gameQuestions;
    }

    public ArrayList[] getGenrer() {
        return genrer;
    }

    public void setGenrer(ArrayList[] genrer) {
        this.genrer = genrer;
    }


}
