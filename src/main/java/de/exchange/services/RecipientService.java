package de.exchange.services;

import de.exchange.dto.Recipient;
import de.exchange.entity.RecipientEntity;

import java.util.List;

public interface RecipientService {
    RecipientEntity createRecipient(Recipient user);
    RecipientEntity getRecipients(String userId);
    List<RecipientEntity> getAll();
}
