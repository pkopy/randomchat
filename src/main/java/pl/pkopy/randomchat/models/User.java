package pl.pkopy.randomchat.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.web.socket.WebSocketSession;

import javax.jws.soap.SOAPBinding;

@Getter
@Setter
public class User {
    private String name;
    private WebSocketSession session;

    public User(String name, WebSocketSession session) {
        this.name = name;
        this.session = session;
    }

    public User(){

    }
}
