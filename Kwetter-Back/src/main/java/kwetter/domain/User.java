package kwetter.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "account") @Table(name = "account")
@NamedQueries({
    @NamedQuery(name="Account.findAll",
                query="SELECT u FROM account u"),
    @NamedQuery(name="Account.findByUsername",
                query="SELECT u FROM account u where u.username = :username")
}) 
public class User {

    @Transient
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "web")
    private String web;
    @Column(name = "bio")
    private String bio;
    @Column(name = "img")
    private String img;

    @ManyToMany
    @JoinTable(name = "following", joinColumns = {
        @JoinColumn(name = "following")}, inverseJoinColumns = {
        @JoinColumn(name = "followers")})
    private Collection<User> followers = new ArrayList();
    @ManyToMany(mappedBy = "followers")
    private Collection<User> following = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Collection<Tweet> tweets;

    /*@OneToMany(mappedBy = "userid")
     private Collection<Long> following = new ArrayList();*/
    public User() {
    }

    public User(String naam) {
        //this.id = serialVersionUID++;
        this.name = naam;
        tweets = new ArrayList<>();
    }

    public User(String username, String name, String web, String bio, String img) {
        //this.id = serialVersionUID++;
        this.username = username;
        this.name = name;
        this.web = web;
        this.bio = bio;
        this.img = img;
        tweets = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Collection<User> getFollowing() {
        return Collections.unmodifiableCollection(following);
    }
    
    public Collection<User> getFollowers() {
        return Collections.unmodifiableCollection(followers);
    }

    @XmlElement(name = "following")
    public Collection<Long> getFollowingIds() {
        Collection<Long> followingIds = new ArrayList<>();
        for (User u : following) {
            followingIds.add(u.getId());
        }
        return followingIds;
    }
    
    public void setFollowing(Collection<User> following) {
        this.following = following;
    }

    public Collection<Tweet> getTweets() {
        return Collections.unmodifiableCollection(tweets);
    }

    public void setTweets(Collection<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void addFollowing(User followingUser) {
        if (!amFollowing(followingUser)) {
            this.following.add(followingUser);
        }
        if (!followingUser.isFollower(this)) {
            followingUser.addFollower(this);
        }
    }

    public void addFollower(User follower) {
        if (!isFollower(follower)) {
            this.followers.add(follower);
        }
        if (!follower.amFollowing(this)) {
            follower.addFollowing(this);
        }
    }  

    public Boolean isFollower(User posFollower) {
        if (this.followers.contains(posFollower)) {
            return true;
        }
        return false;
    }

    public Boolean amFollowing(User posFollowing) {
        if (this.following.contains(posFollowing)) {
            return true;
        }
        return false;
    }

    public Boolean addTweet(Tweet tweet) {
        tweet.setUser(this);
        return this.tweets.add(tweet);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() + bio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the name fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "twitter.domain.User[naam=" + name + "]";
    }

}
