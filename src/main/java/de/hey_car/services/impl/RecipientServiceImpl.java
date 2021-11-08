package de.hey_car.services.impl;

import de.hey_car.dto.Recipient;
import de.hey_car.repository.RecipientRepository;
import de.hey_car.repository.entity.RecipientEntity;
import de.hey_car.services.RecipientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipientServiceImpl implements RecipientService {
    RecipientRepository recipientRepository;

    @Override
    public void createRecipient(Recipient recipient) {
        recipientRepository.save(inbound(recipient));
    }

    @Override
    public RecipientEntity getRecipients(String userId) {
        return recipientRepository.findByUserId(userId);
    }

    private RecipientEntity inbound(Recipient recipient) {
        return RecipientEntity.builder().name(recipient.getName())
                .number(recipient.getNumber())
                .code(recipient.getCode())
                .currency(recipient.getCurrency()).institution(recipient.getInstitution())
                .institutionType(recipient.getInstitutionType()).address(recipient.getAddress())
                .userId(recipient.getUserId()).build();
    }
}
