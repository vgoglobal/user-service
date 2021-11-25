package de.exchange.controllers;

import de.exchange.dto.Complaint;
import de.exchange.entity.ComplaintEntity;
import de.exchange.services.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/complaint")
public class ComplaintController {
    private static final Logger LOGGER = LogManager.getLogger(ComplaintController.class);

    @Autowired
    private ComplaintService complaintService;

    /**
     * Method to
     *
     * @return
     */
    @PostMapping(value = "/")
    public ResponseEntity<ComplaintEntity> createComplaint(@RequestBody @Valid Complaint complaint) {
        LOGGER.info("Processing createComplaint ");
        return ResponseEntity.ok().body(complaintService.createComplaint(complaint));
    }

    /**
     * Method to
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ComplaintEntity> updateComplaint(@RequestBody @Valid Complaint complaint, @PathVariable String id) {
        LOGGER.info("updateComplaint processing ");
        return ResponseEntity.ok().body(complaintService.updateComplaint(complaint, id));
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<ComplaintEntity>> getAll() {
        LOGGER.info("get all complaints ");
        return ResponseEntity.ok().body(complaintService.getAll());
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ComplaintEntity> getById(@PathVariable String id) {
        LOGGER.info("get compliant by id");
        return ResponseEntity.ok().body(complaintService.getComplaintById(id));
    }
}
