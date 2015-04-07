/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.socket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import kwetter.dao.TweetDAO;
import kwetter.dao.TweetDOA_JPAQualifier;
import kwetter.domain.Tweet;
import kwetter.event.TweetEvent;
import kwetter.socket.encoders.TweetMessageEncoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nick
 */
@ServerEndpoint(
        value = "/kwetterendpoint",
        encoders = {}
)
public class KwetterWebsocketEndpoint {
    private static final Logger LOG = Logger.getLogger(KwetterWebsocketEndpoint.class.getName());
    private static final Map<Session, String> peers = Collections.synchronizedMap(new HashMap<Session, String>());
      
    //@Inject @TweetDOA_JPAQualifier
    //private TweetDAO tweetDAO;
    
    @OnMessage
    public void onMessage(final Session client, final String message) throws JSONException, EncodeException {
        if (message != null) {
            LOG.info(message);
            
            JSONObject json = new JSONObject(message);
            String username = json.getString("username");
            String tweetText = json.getString("tweet");    
            TweetEvent event = new TweetEvent(username,tweetText);
            TweetMessageEncoder encoder = new TweetMessageEncoder();
            
            String newMessage = encoder.encode(event);
            LOG.info(newMessage);
            sentToAllExceptMe(client, newMessage);
        }
    }
    
    private void sendMessage(Session peer, Object send) {

        try {
            if (peer.isOpen()) {
                peer.getBasicRemote().sendObject(send);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @OnOpen
    public void onOpen(Session peer) {
        LOG.info("Connection opened ...");
        peers.put(peer, null);
    }

    @OnClose
    public void onClose(Session peer) {
        LOG.info("Connection closed ...");
        peers.remove(peer);
    }

    @OnError
    public void onError(Throwable t) {
        LOG.log(Level.INFO, "Foutje ...{0}", t.getMessage());
    }
    
    private void sentToAll(Object answer) {
        for(Session peer : peers.keySet()){
            sendMessage(peer, answer);
        }
    }
    
    private void sentToAllExceptMe(Session client ,Object answer) {
        for(Session peer : peers.keySet()){
            if(peer != client)
                sendMessage(peer, answer);
        }
    }
}
