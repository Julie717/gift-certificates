package com.project.sa.dao.impl;

import com.project.sa.dao.Queries;
import com.project.sa.dao.UserDao;
import com.project.sa.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_USERS, User.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findBySurname(String surname,Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_USER_BY_SURNAME, User.class)
                .setParameter(1,surname)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException();
    }
}