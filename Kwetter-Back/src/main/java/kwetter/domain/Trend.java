/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nick
 */
@XmlRootElement
public class Trend {
    private String hashtag;
    private Collection<Tweet> tweets = new ArrayList<>();

    public Trend(){}
    
    public Trend(String hashtag) {
        this.hashtag = hashtag;
    }
    
    public Trend(String hashtag, Collection<Tweet> tweets) {
        this.hashtag = hashtag;
        this.tweets = tweets;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Collection<Tweet> getTweets() {
        return Collections.unmodifiableCollection(tweets);
    }

    public void setTweets(Collection<Tweet> tweets) {
        this.tweets = tweets;
    } 
    
    public void addTweet(Tweet tweet){
        this.tweets.add(tweet);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hashtag != null ? hashtag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Trend)) {
            return false;
        }
        Trend other = (Trend) object;
        return this.hashCode() == other.hashCode();
    }
    
    @Override
    public String toString() {
        return "twitter.domain.Trend[id=" + hashtag + "]";
    }
}