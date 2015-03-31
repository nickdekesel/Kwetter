package kwetter.dao;

import kwetter.event.TweetEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.Trend;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Stateless
@TweetDOA_JPAQualifier
public class TweetDAO_JPAImpl implements TweetDAO {
    @PersistenceContext
    private EntityManager em;
    
    @Inject 
    @UserDOA_JPAQualifier
    UserDAO userDAO;
    
    public TweetDAO_JPAImpl() {
    }

    @Override
    public int count() {
        return findAll().size();
    }      
    
    @Override
    public void onTweet(@Observes @TweetDOA_JPAQualifier TweetEvent event){
        User usr = userDAO.find(event.getUsername());
        Tweet tweet = new Tweet(event.getTweet(), new Date(), "PC");
        tweet.setUser(usr);
        //usr.addTweet();
        em.persist(tweet);
    }

    @Override
    public Collection<Tweet> findAll() {
        Query query = em.createNamedQuery("Tweet.findAll");
        return query.getResultList();
    }
    
    @Override
    public Collection<Tweet> findAllFrom(String username) {
        Query query = em.createNamedQuery("Tweet.findAllFrom")
                .setParameter("user", userDAO.find(username));
        return query.getResultList();
    }
    
    @Override
    public Collection<Tweet> findAllFrom(Long userId) {
        Query query = em.createNamedQuery("Tweet.findAllFrom")
                .setParameter("user", userDAO.find(userId));
        return query.getResultList();
    }

    
    @Override
    public Collection<Tweet> getMentions(String username) {
        Query query = em.createNamedQuery("Tweet.findMentions")
                .setParameter("username", "%@"+username +"%" );
        return query.getResultList();
    }

    @Override
    public Collection<Trend> getTrends() {
        List<Trend> trends = new ArrayList<>();
        
        Query query = em.createNamedQuery("Tweet.findTrends");
        for(Object res: query.getResultList()){
            Tweet tweet = (Tweet)res;
            if(checkDate(tweet.getDate())){
                int hashtagIndex = tweet.getTweet().indexOf("#");
                int endIndex = tweet.getTweet().indexOf(" ", hashtagIndex);
                if(endIndex ==-1){
                    endIndex = tweet.getTweet().length();
                }
                String hashtag = tweet.getTweet().substring(hashtagIndex, endIndex);
                boolean doesExist = false;                   

                //push new tweet to trendings
                for (Trend trend : trends) {
                    if (trend.getHashtag().equals(hashtag)) {
                        trend.addTweet(tweet);
                        doesExist = true;
                    }
                }
                if(!doesExist){
                    Trend newTrend = new Trend(hashtag);
                    newTrend.addTweet(tweet);
                    trends.add(newTrend);
                }           
            }
        }
        return trends;
    }    
    
    public boolean checkDate(Date date){
        int minutes = 1000 * 60;
        int hours = minutes * 60;
        int days = hours * 24;
        Date d = new Date();
        
        float howManyDays = (d.getTime() - date.getTime())/days;
        if(howManyDays>7){            
            return false;
        }else{
            return true;
        }
    }  
    
    @Override
    public Collection<Tweet> getTimeLine(Long userid, Collection<User> following) {
        List<Tweet> timelineTweets = new ArrayList<>();
        
        for(User u : following){       
            timelineTweets.addAll(findAllFrom(u.getId()));
        }
        timelineTweets.addAll(findAllFrom(userid));
        return timelineTweets;
    }

    
    @Override
    public Tweet find(Long UserId, Long TweetId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tweet find(String username, Long TweetId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(User user, Tweet tweet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean create(String username, String tweet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public void edit(String username, Tweet tweet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }
}
