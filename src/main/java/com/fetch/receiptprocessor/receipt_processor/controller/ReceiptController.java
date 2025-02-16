package com.fetch.receiptprocessor.receipt_processor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import com.fetch.receiptprocessor.receipt_processor.model.Receipt;
import com.fetch.receiptprocessor.receipt_processor.service.ReceiptService;


@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private static final Logger log =  LoggerFactory.getLogger(ReceiptController.class);

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<String> processReceipt(@RequestBody ReceiptDTO receiptDTO) {
        try {
            log.info("Received JSON: {}", receiptDTO);
            String receiptId = receiptService.saveReceipt(receiptDTO);
            
            return ResponseEntity.ok(receiptId);
        } catch (Exception e) {
            log.error("Error processing receipt: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Integer> getReceiptPoints(@PathVariable String id) {
        try {
            Receipt receipt = receiptService.getReceiptById(id);
            if (receipt != null) {
                return ResponseEntity.ok(receiptService.calculatePoints(receipt));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error retrieving points: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
