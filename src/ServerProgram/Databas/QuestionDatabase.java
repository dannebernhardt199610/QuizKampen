package ServerProgram.Databas;

import Model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class QuestionDatabase {


    private ArrayList[] genres;
    private ArrayList<Question> movieQuestions;
    private ArrayList<Question> musicQuestions;
    private ArrayList<Question> gameQuestions;
    private ArrayList<Question> javaQuestions;

    public QuestionDatabase(){

        this.genres = new ArrayList[4];

        this.movieQuestions = new ArrayList<>();
        this.musicQuestions = new ArrayList<>();
        this.gameQuestions = new ArrayList<>();
        this.javaQuestions = new ArrayList<>();

        this.genres[0] = this.musicQuestions;
        this.genres[1] = this.movieQuestions;
        this.genres[2] = this.gameQuestions;
        this.genres[3] = this.javaQuestions;

        Path[] paths = new Path[4];
        paths[0] = Paths.get("src/ServerProgram/Databas/musicQuestions.txt");
        paths[1] = Paths.get("src/ServerProgram/Databas/movieQuestions.txt");
        paths[2] = Paths.get("src/ServerProgram/Databas/gameQuestions.txt");
        paths[3] = Paths.get("src/ServerProgram/Databas/javaQuestions.txt");

        for (int i = 0; i < paths.length; i++) {

            try(BufferedReader br = Files.newBufferedReader(paths[i], Charset.forName("ISO-8859-1"))){

                String tempString;

                while((tempString = br.readLine()) != null){

                    String question = tempString;

                    String[] answers = new String[4];
                    for (int j = 0; j < 4; j++) {
                        answers[j] = br.readLine();
                    }


                    //Bestämmer kategorin baserat på vilken fil vi läser in
                    String genre = null;

                    if(i == 0)
                        genre = "Musik";
                    if(i == 1)
                        genre = "Film";
                    if(i == 2)
                        genre = "Spel";
                    if(i == 3)
                        genre = "Java";

                    Question questionObject = new Question(question,answers, genre);

                    this.genres[i].add(questionObject);

                }

            }catch(IOException io){
                io.printStackTrace();
            }
        }

        //Blanda alla frågor i varja lista med frågor
        for (List<Question> questionList: this.genres) {
            Collections.shuffle(questionList);
        }

        //Blanda alla listor i arrayen "genres"
        shuffleGenres();
    }

    private void shuffleGenres() {
        Random r = new Random();

        for(int i = 0; i < this.genres.length; i++) {
            int rInt = r.nextInt(this.genres.length);
            List temp = this.genres[i];
            this.genres[i] = this.genres[rInt];
            this.genres[rInt] = (ArrayList) temp;
        }
    }

    public ArrayList<Question> getMovieQuestions() {
        return movieQuestions;
    }


    public ArrayList<Question> getMusicQuestions() {
        return musicQuestions;
    }

    public ArrayList<Question> getGameQuestions() {
        return gameQuestions;
    }


    public ArrayList[] getGenres() {
        return genres;
    }

    public ArrayList<Question> getJavaQuestions() {
        return javaQuestions;
    }
}