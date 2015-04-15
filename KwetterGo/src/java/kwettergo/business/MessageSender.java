package kwettergo.business;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Ahmet
 */
public class MessageSender {

    MessageProducer producer;

    private static final String JNDI_CONNECTION_FACTORY = "jms/__defaultConnectionFactory";
    private static final String JNDI_QUEUE = "jms/KwetterGo/queue";

    public void sendMessage(String tweetText, String username) throws NamingException, JMSException {
        Context jndiContext = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup(JNDI_CONNECTION_FACTORY);
        Queue queue = (Queue) jndiContext.lookup(JNDI_QUEUE);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(queue);

        MapMessage message = session.createMapMessage();
        message.setString("TweetText", tweetText);
        message.setString("User", username);

        producer.send(message);
        producer.close();
    }
}
