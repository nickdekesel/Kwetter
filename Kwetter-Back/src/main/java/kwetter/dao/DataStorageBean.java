/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import kwetter.domain.Tweet;
import kwetter.domain.User;


//@Startup
@Singleton
public class DataStorageBean {
       
    private List<User> users = new ArrayList();
          
    //@PostConstruct
     private void initUsers() {
        User u1 = new User("HansDePans", "Hans", "http", "geboren 1", "https://pbs.twimg.com/profile_images/1466439955/imagesCAMKCXAI_400x400.jpg");
        User u2 = new User("FrankDeTank", "Frank", "httpF", "geboren 2", "images/pod_orange.png");
        User u3 = new User("TomIsStom", "Tom", "httpT", "geboren 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTtVs4zUGJV7EBV0JeRCHmnkvGSYHYHE7SU3woeFMmSpiGpc2s");
        User u4 = new User("SjaakVermaak", "Sjaak", "httpS", "geboren 4", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRribzn3Y1DgTfVkJC4tMnHfFFysfpRBQvD8A8sF9ffURzoV10c3Q");
        /*u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);
        u2.addFollowing(u3);
        u2.addFollowing(u4);
        u3.addFollowing(u1);
        u3.addFollowing(u2);
        u4.addFollowing(u1);
        u4.addFollowing(u2);
        u4.addFollowing(u3);*/

        Tweet t1 = new Tweet("Hallo", new Date(), "PC");
        Tweet t2 = new Tweet("Hallo again", new Date(), "Mobile");
        Tweet t3 = new Tweet("Hallo #ali where are you", new Date(), "PC");
        Tweet t4 = new Tweet("First #hoi Tweet of Frank", new Date(), "Mobile");
        Tweet t5 = new Tweet("Tweet #peter of Frank to @HansDePans", new Date(), "PC");
        Tweet t6 = new Tweet("Hallo #ali where are you", new Date(), "PC");
        u1.addTweet(t1);
        u1.addTweet(t2);
        u1.addTweet(t3);
        u2.addTweet(t4);
        u2.addTweet(t5);
        u4.addTweet(t6);

        this.create(u1);
        this.create(u2);
        this.create(u3);
        this.create(u4);
    }  
      
    public int count() {
        return users.size();
    }

    public void create(User user) {
        users.add(user);
    }
    
    public void edit(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
    public List<User> findAll() {
        return new ArrayList(users);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public User find(Long id) {
        for(User u : users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }
    
    public User find(String username) {
        for(User u : users){
            if(u.getUsername().toLowerCase().equals(username.toLowerCase())){
                return u;
            }
        }
        return null;
    } 
    
    public void createTweet(String username, Tweet tweet){
        User u = find(username);
        remove(u);
        u.addTweet(tweet);
        create(u);
    }

    public Collection<Tweet> findAllTweets() {
        Collection<Tweet> tweets = new ArrayList<>();
        for(User u : users){
            tweets.addAll(u.getTweets());
        }
        return tweets;
    }

    public Collection<Tweet> findAllTweetsFrom(String username) {
        Collection<Tweet> tweets = new ArrayList<>();
        for(User u : users){
            if(u.getUsername().equals(username)){
                tweets.addAll(u.getTweets());
            }
        }
        return tweets;
    }
    
    public Collection<Tweet> findAllTweetsFrom(Long userId) {
        Collection<Tweet> tweets = new ArrayList<>();
        for(User u : users){
            if(u.getId().equals(userId)){
                tweets.addAll(u.getTweets());
            }
        }
        return tweets;
    }
    
    public User getRandomUser(){
        Random rnd = new Random();
        int randomNum = rnd.nextInt(users.size()-1 + 1) ;
        return users.get(randomNum);
    }   
}
