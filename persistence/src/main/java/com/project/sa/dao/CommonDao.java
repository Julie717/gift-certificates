package com.project.sa.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Common dao.
 *
 * @param <T> the type parameter
 */
public interface CommonDao<T> {
    /**
     * Add entity to Db.
     *
     * @param entity is the entity that should add to Db
     * @return the entity that was added to Db
     */
    T add(T entity);

    /**
     * Delete entity from Db.
     *
     * @param entity is the entity that should delete from Db
     */
    void delete(T entity);

    /**
     * Find entity by id in Db.
     *
     * @param id is the entity's id that should find in Db
     * @return the optional of entity that was found in Db
     */
    Optional<T> findById(Long id);

    /**
     * Find all entities in Db.
     *
     * @param limit is the maximum amount of entities that can be found
     * @param offset is the number of entity from which starts search
     * @return the list of entities that was found
     */
    List<T> findAll(Integer limit, Integer offset);
}