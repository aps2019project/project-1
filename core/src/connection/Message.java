package connection;

import model.other.Account;

import java.util.AbstractCollection;
import java.util.Date;

public class Message {

    private Date date;
    private String message;
    private String userName;

    public Message(String message) {
        this.userName = Account.getCurrentAccount().getUsername();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }
}
