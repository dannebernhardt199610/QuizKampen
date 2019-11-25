package ServerUtilities;

import java.io.Serializable;

public class ClientRequest implements Serializable {

    static final long serialVersionUID = 1L;

    public enum TYPE{
        SET_USERNAME,
        MESSAGE_TO_ALL,
        SUBMIT_ANSWER,
        REQUEST_SCORE,
        NOTIFY_READY
    }

    public TYPE type;
    public String message;


    public ClientRequest(TYPE type, String message) {
        this.type = type;
        this.message = message;
    }
}
