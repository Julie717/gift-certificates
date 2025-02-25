package com.project.sa.service.impl;

import com.project.sa.dao.UserDao;
import com.project.sa.exception.ResourceNotFoundException;
import com.project.sa.model.User;
import com.project.sa.model.UserDto;
import com.project.sa.model.converter.impl.UserConverterImpl;
import com.project.sa.service.UserService;
import com.project.sa.util.ErrorMessageReader;
import com.project.sa.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserConverterImpl userConverter;

    @Override
    public List<UserDto> findAll(Pagination pagination) {
        List<User> users = userDao.findAll(pagination.getLimit(), pagination.getOffset());
        return userConverter.convertTo(users);
    }

    @Override
    public UserDto findById(Long id) {
        User User = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageReader.RESOURCE_NOT_FOUND, id,
                        User.class.getSimpleName()));
        return userConverter.convertTo(User);
    }

    @Override
    public List<UserDto> findBySurname(Pagination pagination, String surname) {
        List<User> users = userDao.findBySurname(surname, pagination.getLimit(), pagination.getOffset());
        return userConverter.convertTo(users);
    }
}