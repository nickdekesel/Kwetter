package kwetter.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import kwetter.domain.User;

@Stateless
@UserDOA_CollectionQualifier
public class UserDAO_CollectionImpl implements UserDAO {
    @Inject
    DataStorageBean dsb;
    
    public UserDAO_CollectionImpl() {
    }

    @Override
    public int count() {
        return dsb.count();
    }

    @Override
    public void create(User user) {
        dsb.create(user);
    }

    @Override
    public void edit(User user) {
       dsb.edit(user);
    }

    @Override
    public List<User> findAll() {
        return dsb.findAll();
    }

    @Override
    public void remove(User user) {
        dsb.remove(user);
    }

    @Override
    public User find(Long id) {
        return dsb.find(id);
    }   
    
    @Override
    public User find(String username) {
        return dsb.find(username);
    }   

    @Override
    public User getRandom() {
        return dsb.getRandomUser();
    }

    @Override
    public List<User> getFollowers(Long userid) {
        List<User> followers = new ArrayList<>();
        for(User u : dsb.findAll()){
            if(u.getFollowingIds().contains(userid)){
                followers.add(u);
            }
        }
        return followers;
    }  
    
    @Override
    public Collection<User> getFollowing(Long userid) {        
        return find(userid).getFollowing();
    }   

    @Override
    public void initUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
