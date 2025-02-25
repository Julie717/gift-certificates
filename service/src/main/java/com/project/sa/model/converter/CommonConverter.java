package com.project.sa.model.converter;

import java.util.List;

/**
 * The interface Common converter is used to convert entities to DTO objects.
 *
 * @param <S> the type parameter
 * @param <T> the type parameter
 */
public interface CommonConverter<S, T> {
    /**
     * Convert entity from class S to class T.
     *
     * @param entity is the entity that should be converted
     * @return the new entity of class T
     */
    T convertTo(S entity);

    /**
     * Convert entity from class T to class S.
     *
     * @param  entity is the entity that should be converted
     * @return the new entity of class S
     */
    S convertFrom(T entity);

    /**
     * Convert list of entities from class S to class T.
     *
     * @param entities are the list of entities that should be converted
     * @return the new list of entities of class T
     */
    List<T> convertTo(List<S> entities);

    /**
     * Convert ist of entities from class T to class S.
     *
     * @param  entities are the list of entities that should be converted
     * @return the new list of entities of class S
     */
    List<S> convertFrom(List<T> entities);
}