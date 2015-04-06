/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.dao;

import kwetter.domain.Role;
import kwetter.domain.User;

/**
 *
 * @author Nick
 */
public interface SecurityDAO
{
    void addRole(Role role);

    void addUserRole(User user, Role role);

    Role getRole(String name);
}