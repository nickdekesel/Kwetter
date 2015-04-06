package kwetter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

@Entity(name="tweet")  @Table(name="tweet")
@NamedQueries({
    @NamedQuery(name="Tweet.findAll",
                query="select t from tweet t"),
    @NamedQuery(name="Tweet.findAllFrom",
                query="select t from tweet t where t.user = :user"),
    @NamedQuery(name="Tweet.findMentions",
                query="select t from tweet t where t.tweet like :username"),
    @NamedQuery(name="Tweet.findTrends",
                query="select t from tweet t where t.tweet like '%#%'")
}) 
public class Tweet {
    @Transient
    private static long serialVersionUID = 1L;
    
    @Column(name = "id") 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tweet")
    private String tweet;
    @Column(name = "postDate") @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
    @Column(name = "postedFrom")
    private String postedFrom;   
    @JoinColumn(name="user") @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public Tweet() {
    }

    public Tweet(String tweet) {
        //this.id = serialVersionUID++;
        this.tweet = tweet;
    }
    
    public Tweet(String tweet, Date date, String from) {
        //this.id = serialVersionUID++;
        this.tweet = tweet;
        this.postDate = date;
        this.postedFrom = from;
    }  

    @XmlElement(name = "username")
    public String getUsername() {
        return this.user.getUsername();
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getDate() {
        return postDate;
    }

    public void setDate(Date date) {
        this.postDate = date;
    }

    public String getPostedFrom() {
        return postedFrom;
    }

    public void setPostedFrom(String from) {
        this.postedFrom = from;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tweet != null ? tweet.hashCode()+ postDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tweet)) {
            return false;
        }
        Tweet other = (Tweet) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
    }

}
