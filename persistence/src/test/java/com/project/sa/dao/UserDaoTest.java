package com.project.sa.dao;

import com.project.sa.config.DaoConfigTest;
import com.project.sa.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserDaoImpl.class)
@ContextConfiguration(classes = DaoConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    void findAllTest() {
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfUsers = userDao.findAll(limit, offset).size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }

    @Test
    void findByIdTest() {
        Long id = 2L;

        String actualSurname = userDao.findById(id).get().getSurname();

        String expectedSurname = "Petrov";
        assertEquals(expectedSurname, actualSurname);
    }

    @Test
    void findBySurnameTest() {
        String surname = "ov";
        Integer limit = 10;
        Integer offset = 0;

        int actualAmountOfUsers = userDao.findBySurname(surname, limit, offset).size();

        int expectedAmountOfUsers = 3;
        assertEquals(expectedAmountOfUsers, actualAmountOfUsers);
    }
}