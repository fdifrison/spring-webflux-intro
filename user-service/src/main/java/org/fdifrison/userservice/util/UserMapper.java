package org.fdifrison.userservice.util;

import org.fdifrison.userservice.dto.UserDto;
import org.fdifrison.userservice.entity.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getBalance());
    }

    public static User fromDto(UserDto dto) {
        return  new User(dto.id(), dto.name(), dto.balance());
    }

}
