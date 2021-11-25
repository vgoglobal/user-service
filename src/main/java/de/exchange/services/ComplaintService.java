package de.exchange.services;

import de.exchange.dto.Complaint;
import de.exchange.entity.ComplaintEntity;
import de.exchange.entity.RecipientEntity;

import java.util.List;

public interface ComplaintService {
    ComplaintEntity createComplaint(Complaint complaint);
    ComplaintEntity updateComplaint(Complaint complaint, String id);
    List<ComplaintEntity> getAll();
    ComplaintEntity getComplaintById(String id);
}
