package de.hey_car.services;

import de.hey_car.dto.Login;
import de.hey_car.dto.User;
import de.hey_car.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(User user);
    UserEntity updateUser(User user, String id);
    void confirmEmail(String id, String code);
    Optional<UserEntity> getUser(String id);
    UserEntity loginUser(Login login);
}
