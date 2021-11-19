package de.exchange.services.impl;

import de.exchange.dto.Recipient;
import de.exchange.repository.RecipientRepository;
import de.exchange.entity.RecipientEntity;
import de.exchange.services.RecipientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipientServiceImpl implements RecipientService {
    @Autowired
    RecipientRepository recipientRepository;

    @Override
    public RecipientEntity createRecipient(Recipient recipient) {
        return recipientRepository.save(inbound(recipient));
    }

    @Override
    public RecipientEntity getRecipients(String userId) {
        return recipientRepository.findByUserId(userId);
    }

    @Override
    public List<RecipientEntity> getAll() {
        return recipientRepository.findAll();
    }

    private RecipientEntity inbound(Recipient recipient) {
        return RecipientEntity.builder().name(recipient.getName())
                .number(recipient.getNumber())
                .code(recipient.getCode())
                .currency(recipient.getCurrency()).resource(recipient.getInstitution())
                .resourceType(recipient.getInstitutionType()).address(recipient.getAddress())
                .userId(recipient.getUserId()).build();
    }
}
