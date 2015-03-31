package kwetter.dao;

import kwetter.event.TweetEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import kwetter.domain.Trend;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Stateless
@TweetDOA_CollectionQualifier
public class TweetDAO_CollectionImpl implements TweetDAO {
    @Inject
    DataStorageBean dsb;
    
    public TweetDAO_CollectionImpl() {
    }

    @Override
    public int count() {
        return dsb.count();
    }      

    @Override
    public boolean create(String username, String tweet) {
        User usr = dsb.find(username);
        usr.addTweet(new Tweet(tweet, new Date(), "PC"));
        return true;
    }
    
    @Override
    public void onTweet(@Observes TweetEvent event){
        //User usr = dsb.find(event.getUsername());
        //usr.addTweet(new Tweet(event.getTweet(), new Date(), "PC"));
    }

    @Override
    public void edit(String username, Tweet tweet) {
        User usr = dsb.find(username);
        
    }

    @Override
    public Collection<Tweet> findAll() {
        return dsb.findAllTweets();
    }
    
    @Override
    public Collection<Tweet> findAllFrom(String username) {
        return dsb.findAllTweetsFrom(username);
    }
      
    @Override
    public Collection<Tweet> findAllFrom(Long userid) {
        return dsb.findAllTweetsFrom(userid);
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
    public Collection<Tweet> getMentions(String username) {
        List<Tweet> mentions = new ArrayList<>();
        for(int i=0; i<dsb.count(); i++){
            User user = dsb.findAll().get(i);
            for(int j=0; j<user.getTweets().size(); j++){
                Tweet tweet = (Tweet)user.getTweets().toArray()[j];
                if(tweet.getTweet().contains("@"+username)){
                    mentions.add(tweet);
                }
            }
        }
        return mentions;
    }

    @Override
    public Collection<Trend> getTrends() {
        List<Trend> trends = new ArrayList<>();
        for(int i=0; i<dsb.count(); i++){
            User user = dsb.findAll().get(i);
            for(int j=0; j<user.getTweets().size(); j++){
                Tweet tweet = (Tweet)user.getTweets().toArray()[j];
                              
                if(checkDate(tweet.getDate())){
                    if(tweet.getTweet().contains("#")){
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
            timelineTweets.addAll(u.getTweets());
        }
        timelineTweets.addAll(dsb.find(userid).getTweets());
        return timelineTweets;
    }
}
