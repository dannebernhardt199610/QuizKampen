package ServerUtilities;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    static final long serialVersionUID = 1L;

    public enum TYPE{
        MESSAGE_FROM_CLIENT,
        USERNAME_VERIFICATION
    }

    public TYPE type;
    public String message;


    public ServerResponse(TYPE type, String message) {
        this.type = type;
        this.message = message;
    }
}
