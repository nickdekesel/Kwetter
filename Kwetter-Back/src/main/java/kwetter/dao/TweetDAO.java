package kwetter.dao;

import kwetter.event.TweetEvent;
import java.util.Collection;
import kwetter.domain.Trend;
import kwetter.domain.User;
import kwetter.domain.Tweet;

public interface TweetDAO {

    int count();

    boolean create(String username, String tweet);

    void edit(String username, Tweet tweet);

    Collection<Tweet> findAll();
    
    Collection<Tweet> findAllFrom(String username);
    
    Collection<Tweet> findAllFrom(Long userid);

    Tweet find(Long UserId, Long TweetId);
    
    Tweet find(String username, Long TweetId);

    void remove(User user, Tweet tweet);

    Collection<Tweet> getMentions(String username);

    Collection<Trend> getTrends();

    Collection<Tweet> getTimeLine(Long userid, Collection<User> following);

    void onTweet(TweetEvent event);
}
