package com.project.sa.dao;

import com.project.sa.model.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends CommonDao<Tag> {
    /**
     * Find tag by name.
     *
     * @param name is the name of tag
     * @return the optional value of tag
     */
    Optional<Tag> findTagByName(String name);

    /**
     * Find tags by names where tagNames is the list of tag's names that should be found
     *
     * @param tagNames is the tag list of tag's names
     * @return the list of tags
     */
    List<Tag> findTagByNameInRange(List<String> tagNames);

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     *
     * @param limit  is the maximum amount of tags that can be found
     * @param offset is the number of tag from which starts search
     * @return the list
     */
    List<Tag> findTopTag(Integer limit, Integer offset);

    /**
     * Delete tag from gift certificate.
     *
     * @param id is the id of tag
     * @return the boolean value ("true" if tag was deleted)
     */
    boolean deleteTagFromGiftCertificates(Long id);
}