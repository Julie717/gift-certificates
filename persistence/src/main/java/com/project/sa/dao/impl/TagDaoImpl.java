package com.project.sa.dao.impl;

import com.project.sa.dao.Queries;
import com.project.sa.dao.TagDao;
import com.project.sa.model.Tag;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
@Log4j2
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Tag> findAll(Integer limit, Integer offset) {
        Query query = entityManager.createQuery(Queries.SELECT_ALL_TAG, Tag.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        Optional<Tag> tag;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_TAG_BY_NAME);
            query.setParameter(1, name);
            tag = Optional.ofNullable((Tag) query.getSingleResult());
        } catch (NoResultException ex) {
            log.log(Level.ERROR, ex.getMessage());
            tag = Optional.empty();
        }
        return tag;
    }

    @Override
    public List<Tag> findTagByNameInRange(List<String> tagNames) {
        Query query = entityManager.createQuery(Queries.SELECT_TAG_BY_NAME_IN_RANGE, Tag.class)
                .setParameter(1, tagNames);
        return query.getResultList();
    }

    @Override
    public List<Tag> findTopTag(Integer limit, Integer offset) {
        Query query = entityManager.createNativeQuery(Queries.SELECT_TOP_TAG, Tag.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public boolean deleteTagFromGiftCertificates(Long id) {
        return entityManager.createNativeQuery(Queries.DELETE_TAGS_FROM_GIFT_CERTIFICATE_TAG)
                .setParameter(1, id)
                .executeUpdate() > 0;
    }
}