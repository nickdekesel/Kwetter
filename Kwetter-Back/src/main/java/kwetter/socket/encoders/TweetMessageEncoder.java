/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.socket.encoders;

import java.io.StringWriter;
import java.util.Date;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import kwetter.domain.Tweet;
import kwetter.event.TweetEvent;

/**
 *
 * @author Nick
 */
public class TweetMessageEncoder implements Encoder.Text<TweetEvent> {

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(TweetEvent tweet) throws EncodeException {
        StringWriter swriter = new StringWriter();
        Date date = new Date();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                    .write("username", tweet.getUsername())
                    .write("tweet", tweet.getTweet())
                    .write("date", date.toString())
                    .write("postedFrom", "PC")
                    .writeEnd();
        }
        return swriter.toString();
    }
}