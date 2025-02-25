package com.project.sa.dao.impl;

import com.project.sa.dao.GiftCertificateDao;
import com.project.sa.dao.Queries;
import com.project.sa.model.GiftCertificate;
import com.project.sa.model.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
@Log4j2
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(Queries.SELECT_ALL_GIFT_CERTIFICATES, GiftCertificate.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> giftCertificate;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_GIFT_CERTIFICATE_BY_NAME);
            query.setParameter(1, name);
            giftCertificate = Optional.ofNullable((GiftCertificate) query.getSingleResult());
        } catch (NoResultException ex) {
            log.log(Level.ERROR, ex.getMessage());
            giftCertificate = Optional.empty();
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByParameters(String queryLastPart, Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(queryLastPart, GiftCertificate.class)
                .setFirstResult(offset).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> findByTagId(Long idTag, Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(Queries.SELECT_ALL_GIFT_CERTIFICATES_BY_TAG_ID, GiftCertificate.class)
                .setFirstResult(offset).setMaxResults(limit);
        query.setParameter(1, idTag);
        return query.getResultList();
    }

    @Override
    public Optional<GiftCertificate> findByTagIdInGiftCertificate(Long idGiftCertificate, Long idTag) {
        Optional<GiftCertificate> giftCertificate;
        try {
            Query query = entityManager.createQuery(Queries.SELECT_GIFT_CERTIFICATE_BY_TAG_ID);
            query.setParameter(1, idGiftCertificate);
            query.setParameter(2, idTag);
            giftCertificate = Optional.ofNullable((GiftCertificate) query.getSingleResult());
        } catch (NoResultException ex) {
            log.log(Level.ERROR, ex.getMessage());
            giftCertificate = Optional.empty();
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public void deleteTagFromGiftCertificate(Long idGiftCertificate, Long idTag) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, idGiftCertificate);
        Tag tag = giftCertificate.getTags().stream().filter(t -> t.getId().equals(idTag)).findFirst().get();
        giftCertificate.getTags().remove(tag);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }
}