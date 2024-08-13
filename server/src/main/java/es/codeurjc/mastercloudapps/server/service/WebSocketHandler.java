package es.codeurjc.mastercloudapps.server.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.codeurjc.mastercloudapps.server.model.EoloPlant;

@ServerEndpoint("/eoloplants")
@ApplicationScoped
public class WebSocketHandler {

	private static final String SEC_WEBSOCKET_KEY = "sec-websocket-key";

	Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

	ConcurrentMap<Long, Session> usersWs = new ConcurrentHashMap<>();
	AtomicLong nextUserKey = new AtomicLong();
	
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connection established...");
		long userKey = nextUserKey.getAndIncrement();
		usersWs.put(userKey, session);
		session.getAsyncRemote().sendObject("{\"user-key\" : \"" + userKey + "\"}", result ->  {
            if (result.getException() != null) {
                logger.error("Unable to send message: " + result.getException());
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("Session disconnected...");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("Error: " + throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("Message received: " + message);
    }

	public void sendUpdate(long id, EoloPlant eoloplant) throws IOException {
		usersWs.get(id).getAsyncRemote().sendObject(jsonify(eoloplant));
	}

	public String jsonify(EoloPlant eoloplant) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(eoloplant);
	}
}