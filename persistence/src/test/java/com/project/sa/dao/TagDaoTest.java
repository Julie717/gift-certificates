package com.project.sa.dao;

import com.project.sa.config.DaoConfigTest;
import com.project.sa.dao.impl.TagDaoImpl;
import com.project.sa.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = TagDaoImpl.class)
@ContextConfiguration(classes = DaoConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TagDaoTest {
    @Autowired
    private TagDao tagDao;

    @Test
    void findAllTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        expected.add(new Tag(2L, "sport"));
        expected.add(new Tag(3L, "jumping"));
        expected.add(new Tag(4L, "riding"));
        expected.add(new Tag(5L, "wonderful gift"));
        expected.add(new Tag(6L, "relax"));
        expected.add(new Tag(7L, "make you fun"));
        Integer limit = 50;
        Integer offset = 0;

        List<Tag> actual = tagDao.findAll(limit, offset);

        assertEquals(expected, actual);
    }

    @Test
    void findAllFromOffsetPositionTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(4L, "riding"));
        expected.add(new Tag(5L, "wonderful gift"));
        Integer limit = 2;
        Integer offset = 3;

        List<Tag> actual = tagDao.findAll(limit, offset);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        Long id = 2L;

        Optional<Tag> actual = tagDao.findById(id);

        Tag tag = new Tag(2L, "sport");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNotFound() {
        Long id = 25L;

        Optional<Tag> actual = tagDao.findById(id);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameTestPositive() {
        String name = "gift";

        Optional<Tag> actual = tagDao.findTagByName(name);

        Tag tag = new Tag(1L, "gift");
        Optional<Tag> expected = Optional.of(tag);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameTestNotFound() {
        String name = "skating";

        Optional<Tag> actual = tagDao.findTagByName(name);

        Optional<Tag> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangePositive() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        expected.add(new Tag(3L, "jumping"));
        expected.add(new Tag(6L, "relax"));
        List<String> tagNames = new ArrayList<>();
        tagNames.add("gift");
        tagNames.add("skating");
        tagNames.add("jumping");
        tagNames.add("relax");
        tagNames.add("fitness");

        List<Tag> actual = tagDao.findTagByNameInRange(tagNames);

        assertEquals(expected, actual);
    }

    @Test
    void findTagByNameInRangeNotFound() {
        List<Tag> expected = new ArrayList<>();
        List<String> tagNames = new ArrayList<>();
        tagNames.add("skating");
        tagNames.add("fitness");

        List<Tag> actual = tagDao.findTagByNameInRange(tagNames);

        assertEquals(expected, actual);
    }

    @Test
    void findTopTagTest() {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1L, "gift"));
        Integer limit = 10;
        Integer offset = 0;

        List<Tag> actual = tagDao.findTopTag(limit, offset);

        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void addTest() {
        String name = "funny";
        Tag tag = new Tag();
        tag.setName(name);

        Tag actual = tagDao.add(tag);

        Tag expected = new Tag();
        expected.setId(8L);
        expected.setName(name);
        assertEquals(expected, actual);
    }

    @Test
    @Transactional
    void deleteFromGiftCertificateTagPositive() {
        Long id = 4L;

        boolean actual = tagDao.deleteTagFromGiftCertificates(id);

        assertTrue(actual);
    }

    @Test
    @Transactional
    void deleteFromGiftCertificateTagNegative() {
        Long id = 44L;

        boolean actual = tagDao.deleteTagFromGiftCertificates(id);

        assertFalse(actual);
    }
}