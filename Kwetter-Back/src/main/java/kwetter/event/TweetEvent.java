/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.event;

/**
 *
 * @author Nick
 */
public class TweetEvent {
    private final String username;
    private final String tweet;
    
    public TweetEvent(String username, String tweet){
        this.username = username;
        this.tweet = tweet;
    }

    public String getUsername() {
        return username;
    }

    public String getTweet() {
        return tweet;
    }
}
