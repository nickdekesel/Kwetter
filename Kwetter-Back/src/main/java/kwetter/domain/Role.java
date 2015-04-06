/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Nick
 */
@Entity(name="role") @Table(name="role")
public class Role
{
    @Id @Column(name = "rolename")
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList();

    public Role()
    {

    }

    public Role(String name)
    {
        this.name = name;
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }

    public String getName()
    {
        return name;
    }

    public List<User> getUsers()
    {
        return users;
    }
}