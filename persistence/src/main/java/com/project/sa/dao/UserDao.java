package com.project.sa.dao;

import com.project.sa.model.User;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao extends CommonDao<User> {
    /**
     * Find user by surname.
     *
     * @param surname is the surname of user
     * @param limit   is the maximum amount of users that can be found
     * @param offset  is the number of user from which starts search
     * @return the list
     */
    List<User> findBySurname(String surname, Integer limit, Integer offset);
}