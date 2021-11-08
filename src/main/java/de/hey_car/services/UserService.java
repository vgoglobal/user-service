package de.hey_car.services;

import de.hey_car.dto.Recipient;
import de.hey_car.dto.User;
import de.hey_car.repository.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(User user);
    void confirmEmail(String id, String code);
    Optional<UserEntity> getUser(String id);
}
