package ServerUtilities;

import java.io.Serializable;

public class ClientRequest implements Serializable {

    static final long serialVersionUID = 1L;

    public enum TYPE{
        SEND_USERNAME,
        MESSAGE_TO_ALL,
        SUBMIT_ANSWER,
        REQUEST_SCORE,
        VERIFY_USERNAME //Asks the server to check that the username is not taken
    }

    public TYPE type;
    public String message;


    public ClientRequest(TYPE type, String message) {
        this.type = type;
        this.message = message;
    }
}
