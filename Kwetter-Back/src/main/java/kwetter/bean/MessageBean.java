package kwetter.bean;

import kwetter.domain.Tweet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import kwetter.service.KwetterService;

/**
 *
 * @author vanGrinsven
 */
@MessageDriven(mappedName = "jms/KwetterGo/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener {

    @Inject
    KwetterService ks;

    public MessageBean() {
    }

    public void onMessage(Message message) {
        //TextMessage msg = (TextMessage) message;
        try {
            MapMessage mapMsg = (MapMessage) message;
            System.out.println("username: "+mapMsg.getString("User")+", Tweet: "+mapMsg.getString("TweetText"));
            ks.addTweet(mapMsg.getString("User"), mapMsg.getString("TweetText"));
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
