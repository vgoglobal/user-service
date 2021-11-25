package de.exchange.services.impl;

import de.exchange.dto.Complaint;
import de.exchange.entity.ComplaintEntity;
import de.exchange.repository.ComplaintRepository;
import de.exchange.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Override
    public ComplaintEntity createComplaint(Complaint complaint) {
        return complaintRepository.save(inbound(complaint));
    }

    @Override
    public ComplaintEntity updateComplaint(Complaint complaint, String id) {
        if (complaintRepository.findById(id).isPresent()) {
            return complaintRepository.save(inbound(complaint));
        } else {
            return null;
        }
    }

    @Override
    public List<ComplaintEntity> getAll() {
        return complaintRepository.findAll();
    }

    @Override
    public ComplaintEntity getComplaintById(String id) {
        return complaintRepository.findById(id).orElseThrow();
    }

    private ComplaintEntity inbound(Complaint complaint) {
        return ComplaintEntity.builder().orderId(complaint.getOrderId())
                .comments(complaint.getComments())
                .status(complaint.getStatus())
                .resolvedBy(complaint.getResolvedBy())
                .userId(complaint.getUserId())
                .subject(complaint.getSubject()).build();
    }
}
