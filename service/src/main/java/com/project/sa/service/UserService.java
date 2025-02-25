package com.project.sa.service;

import com.project.sa.model.UserDto;
import com.project.sa.util.Pagination;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Find all users.
     *
     * @param pagination contains limit and offset for search
     * @return the list of user DTO
     */
    List<UserDto> findAll(Pagination pagination);

    /**
     * Find user by id.
     *
     * @param id is the id of user
     * @return the user DTO
     */
    UserDto findById(Long id);

    /**
     * Find users by surname.
     *
     * @param pagination contains limit and offset for search
     * @param surname    is the user's surname
     * @return the list of users
     */
    List<UserDto> findBySurname(Pagination pagination, String surname);
}