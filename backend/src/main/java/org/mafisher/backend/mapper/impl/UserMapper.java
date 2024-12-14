package org.mafisher.backend.mapper.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.response.User;
import org.mafisher.backend.entity.UserEntity;
import org.mafisher.backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper implements Mapper<UserEntity, User> {

    private final ModelMapper modelMapper;

    @Override
    public User mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public UserEntity mapFrom(User user) {
        return modelMapper.map(user, UserEntity.class);
    }
}
