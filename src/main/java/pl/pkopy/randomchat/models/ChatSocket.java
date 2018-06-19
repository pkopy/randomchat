package pl.pkopy.randomchat.models;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component

public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer {

    private List<User> userList = new ArrayList<>();
    private List<TextMessage> messagesList = new ArrayList<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
            webSocketHandlerRegistry.addHandler(this, "/room")
                                    .setAllowedOrigins("*");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {



                User user1 = new User();
                user1.setSession(session);
                userList.add(user1);



//        int length = messagesList.size();
//        if(messagesList.size()>10) {
//            length = 10;
//        }
        if (messagesList.size() > 0) {

            for (int i = messagesList.size()-10; i < messagesList.size() ; i++) {
                session.sendMessage(new TextMessage(messagesList.get(i).getPayload()));
            }



        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userList.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        messagesList.add(message);

        userList.forEach(s ->{

            try {
                if(s.getSession() == session && s.getName()==null){
                    s.setName(message.getPayload());
                }
            s.getSession().sendMessage(new TextMessage(s.getName() + ": "+message.getPayload()));

            }catch (IOException e){
                e.printStackTrace();
            }
        });


    }
}
