package kwetter.dao;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import kwetter.domain.Role;
import kwetter.domain.User;

//@Alternative
@Stateless
@UserDOA_JPAQualifier
public class UserDAO_JPAImpl implements UserDAO {
    @PersistenceContext
    private EntityManager em;
    
    @Inject @SecurityDOA_JPAQualifier
    private SecurityDAO securityDAO;
    
    public UserDAO_JPAImpl() {
    }
    
    @Override
    public void initUsers() {
        User u1 = new User("Hans", "Hans", "http", "geboren 1", "https://pbs.twimg.com/profile_images/1466439955/imagesCAMKCXAI_400x400.jpg");
        User u2 = new User("Frank", "Frank", "httpF", "geboren 2", "images/pod_orange.png");
        User u3 = new User("Tom", "Tom", "httpT", "geboren 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTtVs4zUGJV7EBV0JeRCHmnkvGSYHYHE7SU3woeFMmSpiGpc2s");
        User u4 = new User("Sjaak", "Sjaak", "httpS", "geboren 4", "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRribzn3Y1DgTfVkJC4tMnHfFFysfpRBQvD8A8sF9ffURzoV10c3Q");
        
        u1.addFollowing(u2);
        u1.addFollowing(u3);
        u1.addFollowing(u4);
        u2.addFollowing(u3);
        u2.addFollowing(u4);
        u3.addFollowing(u1);
        u3.addFollowing(u2);
        u4.addFollowing(u1);
        u4.addFollowing(u2);
        u4.addFollowing(u3);
        
        Role r1 = new Role("admin");
        Role r2 = new Role("normal");
        securityDAO.addRole(r1);
        securityDAO.addRole(r2);
        
        em.merge(u1);
        em.merge(u2);
        em.merge(u3);
        em.merge(u4);    
        
        securityDAO.addUserRole(u1, r1);
        securityDAO.addUserRole(u2, r2);
        securityDAO.addUserRole(u3, r2);
        securityDAO.addUserRole(u4, r2);
    }  

    @Override
    public int count() {        
        return findAll().size();
    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void edit(User user) {
       em.merge(user);
    }

    @Override
    public List<User> findAll() {       
        Query query = em.createNamedQuery("Account.findAll");
        return (List<User>) query.getResultList();
    }

    @Override
    public void remove(User user) {
        em.remove(user);
        //Query query = em.createQuery("DELETE FROM User u WHERE u.id = :id");
        //query.setParameter("id", user.getId()).executeUpdate();
    }

    @Override
    public User find(Long id) {
        return em.find(User.class, id);
    }   
    
    @Override
    public User find(String username) {
        User u = em.createNamedQuery("Account.findByUsername", User.class)
                 .setParameter("username", username).getSingleResult();
        return u;
    }   

    @Override
    public User getRandom() {
        Random rnd = new Random();
        List<User> allUsers = findAll();
        int randomNum = rnd.nextInt(allUsers.size()-1 + 1) ;
        return allUsers.get(randomNum);
    }

    @Override
    public Collection<User> getFollowers(Long userid) {
        //Query q = em.createQuery("Select f from following f where f.following = :userId")
        //        .setParameter("userId", userid);
        //List<User> followers = (List<User>) q.getResultList();
        User usr = find(userid);
        return usr.getFollowers();
    }  
    
    @Override
    public Collection<User> getFollowing(Long userid) {           
        //Query q = em.createQuery("Select f from following f where f.follower = :userId")
        //        .setParameter("userId", userid);
        //List<User> following = (List<User>) q.getResultList();
        User usr = find(userid);
        return usr.getFollowing();
    }   
}
