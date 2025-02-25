package com.project.sa.model.converter.impl;

import com.project.sa.model.Tag;
import com.project.sa.model.TagDto;
import com.project.sa.model.converter.CommonConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverterImpl implements CommonConverter<Tag, TagDto> {
    @Override
    public TagDto convertTo(Tag entity) {
        return new TagDto(entity.getId(), entity.getName());
    }

    @Override
    public Tag convertFrom(TagDto entity) {
        Tag tag = new Tag();
        tag.setName(entity.getName());
        if (entity.getId() != null) {
            tag.setId(entity.getId());
        }
        return tag;
    }

    @Override
    public List<TagDto> convertTo(List<Tag> entities) {
        List<TagDto> tagsDto = new ArrayList<>();
        entities.forEach(t -> tagsDto.add(convertTo(t)));
        return tagsDto;
    }

    @Override
    public List<Tag> convertFrom(List<TagDto> entities) {
        List<Tag> tags = new ArrayList<>();
        entities.forEach(t -> tags.add(convertFrom(t)));
        return tags;
    }
}