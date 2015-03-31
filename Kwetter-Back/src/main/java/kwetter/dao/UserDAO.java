package kwetter.dao;

import java.util.Collection;
import kwetter.domain.User;
import java.util.List;

public interface UserDAO {

    int count();

    void create(User user);

    void edit(User user);

    List<User> findAll();

    User find(Long id);
    
    User find(String username);

    void remove(User user);

    User getRandom();

    Collection<User> getFollowers(Long userid);

    Collection<User> getFollowing(Long userid);

    void initUsers();
}
