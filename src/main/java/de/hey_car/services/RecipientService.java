package de.hey_car.services;

import de.hey_car.dto.Recipient;
import de.hey_car.entity.RecipientEntity;

import java.util.List;

public interface RecipientService {
    RecipientEntity createRecipient(Recipient user);
    RecipientEntity getRecipients(String userId);
    List<RecipientEntity> getAll();
}
