package kwetter.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kwetter.domain.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-04T16:48:47")
@StaticMetamodel(Tweet.class)
public class Tweet_ { 

    public static volatile SingularAttribute<Tweet, Long> id;
    public static volatile SingularAttribute<Tweet, String> postedFrom;
    public static volatile SingularAttribute<Tweet, Date> postDate;
    public static volatile SingularAttribute<Tweet, String> tweet;
    public static volatile SingularAttribute<Tweet, User> user;

}