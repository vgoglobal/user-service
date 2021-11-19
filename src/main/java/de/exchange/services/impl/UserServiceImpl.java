package de.exchange.services.impl;

import de.exchange.dto.Address;
import de.exchange.dto.Login;
import de.exchange.dto.User;
import de.exchange.entity.AddressEntity;
import de.exchange.repository.UserRepository;
import de.exchange.entity.UserEntity;
import de.exchange.services.UserService;
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
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate Email ");
        }
        return userRepository.save(inbound(user));
    }

    @Override
    public UserEntity updateUser(User user, String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserEntity newUserEntity = inbound(user);
            newUserEntity.setId(id);
            userRepository.save(newUserEntity);
        }
        return null;
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

    @Override
    public UserEntity loginUser(Login login) {
        Optional<UserEntity> userEntity = userRepository.findByEmailAndPassword(login.getUserName(), login.getPassword());
        if (userEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return userEntity.get();
    }

    private UserEntity inbound(User user) {
        return UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(AddressEntity.builder()
                    .flatNo(user.getAddress().getFlatNo())
                    .address1(user.getAddress().getAddress1())
                    .address2(user.getAddress().getAddress2())
                    .city(user.getAddress().getCity())
                    .country(user.getAddress().getCountry())
                    .pin(user.getAddress().getPin())
                    .state(user.getAddress().getState())
                    .build())
                .country(user.getCountry())
                .status(user.getStatus())
                .confirmationCode(generateString())
                .password(user.getPassword())
                .confirmed(false)
                .createdDate(Instant.now())
                .role(user.getRoles())
                .email(user.getEmail()).build();
    }

    private User outbound(UserEntity user) {
        return User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(Address.builder()
                        .flatNo(user.getAddress().getFlatNo())
                        .address1(user.getAddress().getAddress1())
                        .address2(user.getAddress().getAddress2())
                        .city(user.getAddress().getCity())
                        .country(user.getAddress().getCountry())
                        .pin(user.getAddress().getPin())
                        .state(user.getAddress().getState())
                        .build())
                .country(user.getCountry())
                .email(user.getEmail()).build();
    }

    private static String generateString() {
        return UUID.randomUUID().toString();
    }
}
