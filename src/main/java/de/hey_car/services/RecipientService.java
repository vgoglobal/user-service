package de.hey_car.services;

import de.hey_car.dto.Recipient;
import de.hey_car.dto.User;
import de.hey_car.repository.entity.RecipientEntity;
import de.hey_car.repository.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface RecipientService {
    RecipientEntity createRecipient(Recipient user);
    RecipientEntity getRecipients(String userId);
    List<RecipientEntity> getAll();
}
