/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kwetter.domain.Role;
import kwetter.domain.User;

/**
 *
 * @author Nick
 */
@Stateless
@SecurityDOA_JPAQualifier
public class SecurityDAO_JPAImpl implements SecurityDAO
{
    @PersistenceContext
    private EntityManager em;
 
    @Override
    public void addRole(Role role)
    {
        em.persist(role);
    }

    @Override
    public void addUserRole(User user, Role role)
    {
        if(!user.getRoles().contains(role) && !role.getUsers().contains(user))
        {
            user.addRole(role);
            role.addUser(user);
            em.merge(user);
            em.merge(role);
        }
    }

    @Override
    public Role getRole(String name)
    {
        return em.find(Role.class, name);
    }
}