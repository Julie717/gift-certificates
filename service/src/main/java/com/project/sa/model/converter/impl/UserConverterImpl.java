package com.project.sa.model.converter.impl;

import com.project.sa.model.Purchase;
import com.project.sa.model.PurchaseResponseDto;
import com.project.sa.model.User;
import com.project.sa.model.UserDto;
import com.project.sa.model.converter.CommonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class UserConverterImpl implements CommonConverter<User, UserDto> {
    private final PurchaseResponseConverterImpl purchaseResponseConverter;

    @Override
    public UserDto convertTo(User entity) {
        List<PurchaseResponseDto> purchases = null;
        if (entity.getPurchases() != null && !entity.getPurchases().isEmpty()) {
            purchases = purchaseResponseConverter.convertTo(entity.getPurchases());
        }
        return new UserDto(entity.getId(), entity.getName(), entity.getSurname(), purchases);
    }

    @Override
    public User convertFrom(UserDto entity) {
        List<Purchase> purchases = null;
        if (entity.getPurchases() != null && !entity.getPurchases().isEmpty()) {
            purchases = purchaseResponseConverter.convertFrom(entity.getPurchases());
        }
        return new User(entity.getId(), entity.getName(), entity.getSurname(), purchases);
    }

    @Override
    public List<UserDto> convertTo(List<User> entities) {
        List<UserDto> usersDto = new ArrayList<>();
        entities.forEach(t -> usersDto.add(convertTo(t)));
        return usersDto;
    }

    @Override
    public List<User> convertFrom(List<UserDto> entities) {
        List<User> users = new ArrayList<>();
        entities.forEach(t -> users.add(convertFrom(t)));
        return users;
    }
}