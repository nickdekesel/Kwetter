package kwetter.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kwetter.domain.Tweet;
import kwetter.domain.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-03-27T11:43:41")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> username;
    public static volatile CollectionAttribute<User, User> followers;
    public static volatile CollectionAttribute<User, User> following;
    public static volatile SingularAttribute<User, String> bio;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> img;
    public static volatile SingularAttribute<User, String> web;
    public static volatile CollectionAttribute<User, Tweet> tweets;

}