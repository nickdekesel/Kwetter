package kwetter.service;

import kwetter.dao.SecurityDOA_JPAQualifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import kwetter.dao.SecurityDAO;
import kwetter.dao.TweetDAO;
import kwetter.dao.TweetDOA_JPAQualifier;
import kwetter.event.TweetEvent;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDOA_JPAQualifier;
import kwetter.domain.Role;
import kwetter.domain.Trend;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Stateless
public class KwetterService {

    @Inject
    @UserDOA_JPAQualifier
    private UserDAO userDAO;
    @Inject
    @TweetDOA_JPAQualifier
    private TweetDAO tweetDAO;
    @Inject
    @SecurityDOA_JPAQualifier
    private SecurityDAO securityDAO;
       
    @Inject @TweetDOA_JPAQualifier
    private Event<TweetEvent> tweetEvent;

    public KwetterService() {
    }

    public void create(User user) {
        userDAO.create(user);
    }

    public void edit(User user) {
        userDAO.edit(user);
    }

    public void remove(User user) {
        userDAO.remove(user);
    }

    public List<User> findAll() {
        List<User> allUsers= userDAO.findAll();
        return allUsers;
    }

    public User find(Long id) {
        return userDAO.find(id);
    }
    
    public User find(String username){
        return userDAO.find(username);
    }

    public int count() {
        return userDAO.count();
    }
    
    public Collection<Tweet> findTweets(String username){
        return tweetDAO.findAllFrom(username);
    }
    
    public Collection<Tweet> findTweets(Long userId){
        return tweetDAO.findAllFrom(userId);
    }
    
    public Collection<Tweet> findAllTweets(){
        return tweetDAO.findAll();
    }

    public User getRandomUser() {
        return userDAO.getRandom();
    }

    public Collection<User> getFollowers(Long userid) {
        return userDAO.getFollowers(userid);
    }
    
    public Collection<User> getFollowing(Long userid) {
        return userDAO.getFollowing(userid);
    }

    public Collection<Tweet> getMentions(String username) {
        return tweetDAO.getMentions(username);
    }

    public Collection<Trend> getTrends() {
        return tweetDAO.getTrends();
    }

    public Collection<Tweet> getTimeLine(Long userid) {
        return tweetDAO.getTimeLine(userid, getFollowing(userid));
    }

    public String addTweet(String username, String tweet) {
        tweetEvent.fire(new TweetEvent(username,tweet));
        return "{\"done\":\"true\"}";
        //return tweetDAO.create(username, tweet);
    }

    public void initUsers() {
        userDAO.initUsers();
    }

    public void addRole(String rolename){
        Role role = new Role(rolename);
        securityDAO.addRole(role);
    }

    public void addUserRole(String username, String rolename) {
        User user = userDAO.find(username);
        Role role = securityDAO.getRole(rolename);
        securityDAO.addUserRole(user, role);
    }
    
}