package com.project.sa.service;

import com.project.sa.dao.GiftCertificateDao;
import com.project.sa.exception.IllegalParameterException;
import com.project.sa.exception.ResourceAlreadyExistsException;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.GiftCertificate;
import com.project.sa.model.GiftCertificateDto;
import com.project.sa.model.Tag;
import com.project.sa.model.TagDto;
import com.project.sa.model.converter.impl.GiftCertificateConverterImpl;
import com.project.sa.model.converter.impl.TagConverterImpl;
import com.project.sa.service.impl.GiftCertificateServiceImpl;
import com.project.sa.util.Pagination;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyList;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    private static final GiftCertificate GIFT_CERTIFICATE_SKATING;
    private static final GiftCertificateDto GIFT_CERTIFICATE_SKATING_DTO;
    private static final GiftCertificateDto GIFT_CERTIFICATE_SKATING_DTO_WITHOUT_ID;
    private static final GiftCertificate GIFT_CERTIFICATE_SKATING_WITHOUT_TAG;
    private static final GiftCertificate GIFT_CERTIFICATE_FITNESS;
    private static final GiftCertificateDto GIFT_CERTIFICATE_FITNESS_DTO;
    private static final GiftCertificate GIFT_CERTIFICATE_FITNESS_OTHER_ID;
    private static final GiftCertificateDto GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID;
    private static final GiftCertificateDto GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS;
    private static final GiftCertificate GIFT_CERTIFICATE_FITNESS_FOR_PATCH;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagService tagService;

    @Spy
    private final TagConverterImpl tagConverter = new TagConverterImpl();

    @Spy
    private final GiftCertificateConverterImpl giftCertificateConverter = new GiftCertificateConverterImpl(tagConverter);

    static {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "gift"));
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(7L, "make you fun"));
        GIFT_CERTIFICATE_SKATING = new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tags);
        GIFT_CERTIFICATE_SKATING_WITHOUT_TAG = new GiftCertificate(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
        List<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(1L, "gift"));
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(7L, "make you fun"));
        GIFT_CERTIFICATE_SKATING_DTO = new GiftCertificateDto(1L, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tagsDto);
        GIFT_CERTIFICATE_SKATING_DTO_WITHOUT_ID = new GiftCertificateDto(null, "Skating", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), tagsDto);
        tags = new ArrayList<>();
        tags.add(new Tag(2L, "sport"));
        tags.add(new Tag(5L, "wonderful gift"));
        GIFT_CERTIFICATE_FITNESS = new GiftCertificate(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags);
        GIFT_CERTIFICATE_FITNESS_OTHER_ID = new GiftCertificate(1L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tags);
        tagsDto = new ArrayList<>();
        tagsDto.add(new TagDto(2L, "sport"));
        tagsDto.add(new TagDto(5L, "wonderful gift"));
        GIFT_CERTIFICATE_FITNESS_DTO = new GiftCertificateDto(2L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tagsDto);
        GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID = new GiftCertificateDto(1L, "Fitness", "Physical fitness is a state of health and " +
                "well-being and, more specifically, the ability to perform aspects of sports, " +
                "occupations and daily activities. Physical fitness is generally achieved through" +
                " proper nutrition, moderate-vigorous physical exercise, and sufficient rest.",
                BigDecimal.valueOf(80), 30, Timestamp.valueOf("2021-01-11 10:30:01"),
                Timestamp.valueOf("2021-01-11 10:30:01"), tagsDto);
        GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS = new GiftCertificateDto();
        GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS.setId(1L);
        GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS.setName("Fitness");
        GIFT_CERTIFICATE_FITNESS_FOR_PATCH = new GiftCertificate(1L, "Fitness", "Ice skating is a sport in which people slide " +
                "over a smooth ice surface on steel-bladed skates. Millions of people skate in " +
                "those parts of the world where the winters are cold enough.", BigDecimal.valueOf(10),
                30, Timestamp.valueOf("2021-01-10 12:15:37"),
                Timestamp.valueOf("2021-01-10 12:15:37"), null);
    }

    @Test
    void findAllTestEmptyList() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        Mockito.when(giftCertificateDao.findAll(anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        Pagination pagination = new Pagination(10, 2);

        List<GiftCertificateDto> actual = giftCertificateService.findAll(pagination);

        verify(giftCertificateConverter).convertTo(giftCertificates);
        assertEquals(giftCertificatesDto, actual);
    }

    @Test
    void findAllTestPositive() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(GIFT_CERTIFICATE_SKATING);
        giftCertificates.add(GIFT_CERTIFICATE_FITNESS);
        Mockito.when(giftCertificateDao.findAll(anyInt(), anyInt())).thenReturn(giftCertificates);
        List<GiftCertificateDto> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_SKATING_DTO);
        expected.add(GIFT_CERTIFICATE_FITNESS_DTO);
        Pagination pagination = new Pagination(2, 0);

        List<GiftCertificateDto> actual = giftCertificateService.findAll(pagination);

        verify(giftCertificateConverter, times(1)).convertTo(giftCertificates);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestPositive() {
        GiftCertificateDto expected = GIFT_CERTIFICATE_SKATING_DTO;
        Long id = 2L;
        Optional<GiftCertificate> giftCertificate = Optional.of(GIFT_CERTIFICATE_SKATING);
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(giftCertificate);

        GiftCertificateDto actual = giftCertificateService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdTestNegative() {
        Long id = 25L;
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
    }

    @Test
    void findByParametersTestPositive() {
        List<GiftCertificateDto> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_SKATING_DTO);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        parameters.put("description", "beautiful");
        parameters.put("sort", "nameGiftCertificate,-createDate");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(GIFT_CERTIFICATE_SKATING);
        Mockito.when(giftCertificateDao.findByParameters(anyString(), anyInt(), anyInt())).thenReturn(giftCertificates);
        Pagination pagination = new Pagination(2, 0);

        List<GiftCertificateDto> actual = giftCertificateService.findByParameters(parameters, pagination);

        assertEquals(expected, actual);
    }

    @Test
    void findByParametersTestIncorrectInputParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("color", "gift");
        parameters.put("description", "beautiful");
        Pagination pagination = new Pagination(2, 0);

        assertThrows(IllegalParameterException.class, () -> giftCertificateService.findByParameters(parameters, pagination));
    }

    @Test
    void findByParametersTestNotFound() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nameGiftCertificate", "gift");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        Mockito.when(giftCertificateDao.findByParameters(anyString(), anyInt(), anyInt())).thenReturn(giftCertificates);
        Pagination pagination = new Pagination(2, 0);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findByParameters(parameters, pagination));
    }

    @Test
    void findByTagIdTestPositive() {
        List<GiftCertificateDto> expected = new ArrayList<>();
        expected.add(GIFT_CERTIFICATE_SKATING_DTO);
        Long idTag = 2L;
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(GIFT_CERTIFICATE_SKATING);
        Mockito.when(giftCertificateDao.findByTagId(anyLong(), anyInt(), anyInt()))
                .thenReturn(giftCertificates);
        Pagination pagination = new Pagination(2, 3);

        List<GiftCertificateDto> actual = giftCertificateService.findByTagId(idTag, pagination);

        assertEquals(expected, actual);
    }

    @Test
    void findByTagIdTestNegative() {
        Long idTag = 2L;
        Mockito.when(giftCertificateDao.findByTagId(anyLong(), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        Pagination pagination = new Pagination(2, 3);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findByTagId(idTag, pagination));
    }

    @Test
    void findGiftCertificateByTagIdTestPositive() {
        GiftCertificateDto expected = GIFT_CERTIFICATE_SKATING_DTO;
        Mockito.when(giftCertificateDao.findByTagIdInGiftCertificate(anyLong(), anyLong()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING));

        GiftCertificateDto actual = giftCertificateService.findGiftCertificateByTagId(2L, 2L);

        assertEquals(expected, actual);
    }

    @Test
    void findGiftCertificateByTagIdTestNegative() {
        Mockito.when(giftCertificateDao.findByTagIdInGiftCertificate(anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateByTagId(21L, 5L));
    }


    @Test
    void addTestPositive() {
        Long expectedId = 1L;
        Mockito.when(giftCertificateDao.findByName(GIFT_CERTIFICATE_SKATING.getName())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(GIFT_CERTIFICATE_SKATING);
        Mockito.when(tagService.findByRangeNames(anyList())).thenReturn(GIFT_CERTIFICATE_SKATING_DTO_WITHOUT_ID.getTags());

        GiftCertificateDto actual = giftCertificateService.add(GIFT_CERTIFICATE_SKATING_DTO_WITHOUT_ID);

        assertEquals(expectedId, actual.getId());
    }

    @Test
    void addTestNegative() {
        Mockito.when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(new GiftCertificate()));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Skating");

        assertThrows(ResourceAlreadyExistsException.class, () -> giftCertificateService.add(giftCertificateDto));
    }

    @Test
    void addTagsToGiftCertificateTestPositive() {
        GiftCertificateDto expected = GIFT_CERTIFICATE_SKATING_DTO;
        Long id = 2L;
        Mockito.when(giftCertificateDao.findById(id)).thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING_WITHOUT_TAG));
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(GIFT_CERTIFICATE_SKATING);
        List<TagDto> tagsDto = GIFT_CERTIFICATE_SKATING_DTO.getTags();
        Mockito.when(tagService.findByRangeNames(tagsDto)).thenReturn(new ArrayList<>());

        GiftCertificateDto actual = giftCertificateService.addTagsToGiftCertificate(id, tagsDto);

        assertEquals(expected, actual);
    }

    @Test
    void addTagsToGiftCertificateTestNegative() {
        Long id = 2L;
        Mockito.when(giftCertificateDao.findById(id)).thenThrow(ResourceNotFoundException.class);
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName("Skating");

        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.addTagsToGiftCertificate(id, anyList()));
    }

    @Test
    void addTagsToGiftCertificateTestTagsAlreadyExist() {
        Mockito.when(giftCertificateDao.findById(GIFT_CERTIFICATE_SKATING.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING));
        List<TagDto> tagsDto = GIFT_CERTIFICATE_SKATING_DTO.getTags();

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.addTagsToGiftCertificate(GIFT_CERTIFICATE_SKATING_DTO.getId(), tagsDto));
    }

    @Test
    void updateGiftCertificateTestPositive() {
        String expectedName = GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID.getName();
        Mockito.when(giftCertificateDao.findById(GIFT_CERTIFICATE_SKATING_WITHOUT_TAG.getId()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING_WITHOUT_TAG));
        Mockito.when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(GIFT_CERTIFICATE_FITNESS_OTHER_ID);
        Mockito.when(tagService.findByRangeNames(GIFT_CERTIFICATE_FITNESS_DTO.getTags())).thenReturn(GIFT_CERTIFICATE_FITNESS_DTO.getTags());

        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID);

        assertEquals(expectedName, actual.getName());
    }

    @Test
    void updateGiftCertificateTestNotFound() {
        Mockito.when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(15L);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.updateGiftCertificate(giftCertificateDto));
    }

    @Test
    void updateGiftCertificateTestAlreadyExist() {
        Mockito.when(giftCertificateDao.findById(GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID.getId()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING));
        Mockito.when(giftCertificateDao.findByName(GIFT_CERTIFICATE_FITNESS_OTHER_ID.getName()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_FITNESS));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.updateGiftCertificate(GIFT_CERTIFICATE_FITNESS_DTO_OTHER_ID));
    }

    @Test
    void patchGiftCertificateTestPositive() {
        String expectedName = GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS.getName();
        Mockito.when(giftCertificateDao.findById(GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS.getId()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING_WITHOUT_TAG));
        Mockito.when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(GIFT_CERTIFICATE_FITNESS_FOR_PATCH);

        GiftCertificateDto actual = giftCertificateService.patchGiftCertificate(GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS);

        assertEquals(expectedName, actual.getName());
    }

    @Test
    void patchGiftCertificateTestNotFound() {
        Mockito.when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(15L);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.patchGiftCertificate(giftCertificateDto));
    }

    @Test
    void patchGiftCertificateTestAlreadyExist() {
        Mockito.when(giftCertificateDao.findById(GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS.getId()))
                .thenReturn(Optional.of(GIFT_CERTIFICATE_SKATING_WITHOUT_TAG));
        Mockito.when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(GIFT_CERTIFICATE_FITNESS));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.patchGiftCertificate(GIFT_CERTIFICATE_FITNESS_DTO_NOT_ALL_PARAMETERS));
    }
}