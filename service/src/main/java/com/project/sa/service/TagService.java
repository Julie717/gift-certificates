package com.project.sa.service;

import com.project.sa.model.TagDto;
import com.project.sa.util.Pagination;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Find all tags.
     *
     * @param pagination contains limit and offset for search
     * @return the list of tags
     */
    List<TagDto> findAll(Pagination pagination);

    /**
     * Find tag by id.
     *
     * @param id is the id of tag
     * @return the tag DTO
     */
    TagDto findById(Long id);

    /**
     * Find the most widely used tag of a user with the highest cost of all orders.
     *
     * @param pagination contains limit and offset for search
     * @return the list of tag DTO
     */
    List<TagDto> findTopTag(Pagination pagination);

    /**
     * Add tag to Db.
     *
     * @param tagDto is the tag DTO that should be added
     * @return is the tag DTO that was added
     */
    TagDto add(TagDto tagDto);

    /**
     * Delete tag by id from Db.
     *
     * @param id is the id of tag
     */
    void deleteById(Long id);

    /**
     * Find tags by names.
     *
     * @param tagsDto is the tags DTO that contains names for searching
     * @return the list of tag DTO
     */
    List<TagDto> findByRangeNames(List<TagDto> tagsDto);
}