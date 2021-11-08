package de.hey_car.services.impl;

import de.hey_car.dto.User;
import de.hey_car.repository.UserRepository;
import de.hey_car.repository.entity.UserEntity;
import de.hey_car.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate Email ");
        }
        return userRepository.save(inbound(user));
    }

    @Override
    public void confirmEmail(String id, String code) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.filter(p -> p.getConfirmationCode() != null).orElseThrow();
        UserEntity newUserEntity = userEntity.get();
        newUserEntity.setConfirmed(true);
        userRepository.save(newUserEntity);
    }

    @Override
    public Optional<UserEntity> getUser(String id) {
        return userRepository.findById(id);
    }



    private UserEntity inbound(User user) {
        return UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .country(user.getCountry())
                .confirmationCode(generateString())
                .confirmed(false)
                .createdDate(Instant.now())
                .email(user.getEmail()).build();
    }

    private User outbound(UserEntity user) {
        return User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .country(user.getCountry())
                .email(user.getEmail()).build();
    }

    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
