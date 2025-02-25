package com.project.sa.service.impl;

import com.project.sa.dao.TagDao;
import com.project.sa.model.Tag;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.TagDto;
import com.project.sa.model.converter.impl.TagConverterImpl;
import com.project.sa.service.TagService;
import com.project.sa.util.ErrorMessageReader;
import com.project.sa.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagConverterImpl tagConverter;

    @Override
    public List<TagDto> findAll(Pagination pagination) {
        List<Tag> tags = tagDao.findAll(pagination.getLimit(),pagination.getOffset());
        return tagConverter.convertTo(tags);
    }

    @Override
    public TagDto findById(Long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        return tagConverter.convertTo(tag);
    }

    @Override
    public List<TagDto> findByRangeNames(List<TagDto> tagsDto) {
        List<String> tagNames = tagsDto.stream().map(TagDto::getName).collect(Collectors.toList());
        return tagConverter.convertTo(tagDao.findTagByNameInRange(tagNames));
    }

    public List<TagDto> findTopTag(Pagination pagination){
        List<Tag> tags = tagDao.findTopTag(pagination.getLimit(),pagination.getOffset());
        return tagConverter.convertTo(tags);
    }

    @Override
    @Transactional
    public TagDto add(TagDto tagDto) {
        boolean isExist = tagDao.findTagByName(tagDto.getName()).isPresent();
        if (isExist) {
            throw new ResourceAlreadyExistsException(ErrorMessageReader.TAG_ALREADY_EXISTS,
                    tagDto.getName());
        }
        Tag tag = tagConverter.convertFrom(tagDto);
        return tagConverter.convertTo(tagDao.add(tag));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        Tag.class.getSimpleName()));
        tagDao.deleteTagFromGiftCertificates(id);
        tagDao.delete(tag);
    }
}