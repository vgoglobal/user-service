package de.exchange.services;

import de.exchange.dto.Login;
import de.exchange.dto.User;
import de.exchange.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(User user);
    UserEntity updateUser(User user, String id);
    void confirmEmail(String id, String code);
    Optional<UserEntity> getUser(String id);
    UserEntity loginUser(Login login);
}
