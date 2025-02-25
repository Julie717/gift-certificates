package com.project.sa.controller;

import com.project.sa.model.UserDto;
import com.project.sa.service.UserService;
import com.project.sa.util.HateoasLinkBuilder;
import com.project.sa.util.Pagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllUsers(@Valid @NotNull Pagination pagination,
                                      @Size(min = 1, max = 50) String surname) {
        List<UserDto> users;
        if (surname == null || surname.isEmpty()) {
            users = userService.findAll(pagination);
        } else {
            users = userService.findBySurname(pagination, surname);
        }
        HateoasLinkBuilder.buildUsersLink(users);
        return users;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable @Positive Long id) {
        UserDto user = userService.findById(id);
        HateoasLinkBuilder.buildUserLink(user);
        return user;
    }
}