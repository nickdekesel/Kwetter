package kwetter.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kwetter.domain.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-04T16:48:47")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile ListAttribute<Role, User> users;
    public static volatile SingularAttribute<Role, String> name;

}