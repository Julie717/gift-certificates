package com.project.sa.service;

import com.project.sa.dao.TagDao;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.Tag;
import com.project.sa.model.TagDto;
import com.project.sa.model.converter.impl.TagConverterImpl;
import com.project.sa.service.impl.TagServiceImpl;
import com.project.sa.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDao tagDao;

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Test
    void findAllTestEmptyList() {
        List<Tag> tags = new ArrayList<>();
        Integer limit = 10;
        Integer offset = 2;
        Mockito.when(tagDao.findAll(10, 2)).thenReturn(tags);
        Pagination pagination = new Pagination(limit, offset);
        List<TagDto> tagsDto = new ArrayList<>();

        List<TagDto> actual = tagService.findAll(pagination);

        verify(tagConverter).convertTo(tags);
        assertEquals(tagsDto, actual);
    }

    @Test
    void findAllTestPositive() {
        Integer limit = 3;
        Integer offset = 0;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(3L, "jumping"));
        Mockito.when(tagDao.findAll(limit, offset)).thenReturn(tags);
        Pagination pagination = new Pagination(limit, offset);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(1L, "gift"));
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(3L, "jumping"));

        List<TagDto> actual = tagService.findAll(pagination);

        verify(tagConverter, times(1)).convertTo(tags);
        assertEquals(tagsDto, actual);
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2L;
        Optional<Tag> tag = Optional.of(new Tag(2L, "sport"));
        Mockito.when(tagDao.findById(id)).thenReturn(tag);
        TagDto expected = new TagDto(2L, "sport");

        TagDto actual = tagService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(tagDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
    }

    @Test
    void findByRangeNamesTestPositive() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        tagsDto.add(new TagDto(null, "jumping"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("sport");
        tagNames.add("jumping");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(8L, "jumping"));
        Mockito.when(tagDao.findTagByNameInRange(tagNames)).thenReturn(tags);

        List<TagDto> actual = tagService.findByRangeNames(tagsDto);

        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1L, "gift"));
        expected.add(new TagDto(8L, "jumping"));
        assertEquals(expected, actual);
    }

    @Test
    void findByRangeNamesTestEmpty() {
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(null, "gift"));
        tagsDto.add(new TagDto(null, "sport"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("sport");
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDao.findTagByNameInRange(tagNames)).thenReturn(tags);

        List<TagDto> actual = tagService.findByRangeNames(tagsDto);

        List<TagDto> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void  findTopTagTest() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(8L, "jumping"));
        Integer limit = 3;
        Integer offset = 0;
        Pagination pagination = new Pagination(limit, offset);
        Mockito.when(tagDao.findTopTag(limit,offset)).thenReturn(tags);

        List<TagDto> actual = tagService.findTopTag(pagination);

        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1L, "gift"));
        expected.add(new TagDto(8L, "jumping"));
        assertEquals(expected, actual);
    }

    @Test
    void addTestPositive() {
        Tag tag = new Tag(null, "jumping");
        Mockito.when(tagDao.findTagByName(tag.getName())).thenReturn(Optional.empty());
        Tag tagWithId = new Tag(3L, "jumping");
        Mockito.when(tagDao.add(tag)).thenReturn(tagWithId);
        TagDto tagDto = new TagDto(null, "jumping");

        TagDto actual = tagService.add(tagDto);

        TagDto expected = new TagDto(3L, "jumping");
        assertEquals(expected, actual);
    }

    @Test
    void addTestNegative() {
        Mockito.when(tagDao.findTagByName(anyString())).thenReturn(Optional.of(new Tag()));
        TagDto tagDto = new TagDto();
        tagDto.setName("Skating");

        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.add(tagDto));
    }
}