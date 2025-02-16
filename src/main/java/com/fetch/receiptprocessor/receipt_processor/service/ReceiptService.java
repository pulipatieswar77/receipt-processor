package com.fetch.receiptprocessor.receipt_processor.service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fetch.receiptprocessor.receipt_processor.dto.ReceiptDTO;
import com.fetch.receiptprocessor.receipt_processor.model.Item;
import com.fetch.receiptprocessor.receipt_processor.model.Receipt;
import com.fetch.receiptprocessor.receipt_processor.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReceiptService {

    private static final Logger log = LoggerFactory.getLogger(ReceiptService.class);

    @Autowired
    private ReceiptRepository receiptRepository;

    public String saveReceipt(ReceiptDTO receiptDTO) {
        try {
            Receipt receipt = new Receipt();
            receipt.setId(UUID.randomUUID().toString());
            receipt.setRetailer(receiptDTO.getRetailer());
            receipt.setPurchaseDate(receiptDTO.getPurchaseDate());
            receipt.setPurchaseTime(receiptDTO.getPurchaseTime());
            receipt.setTotalAmount(receiptDTO.getTotalAmount());
            
            List<Item> items = receiptDTO.getItems().stream()
                    .map(dto -> new Item(dto.getShortDescription().trim(), dto.getPrice()))
                    .toList();

            receipt.setItems(items);
            receiptRepository.save(receipt);
            log.info("Receipt saved with ID: {}", receipt.getId());
            return receipt.getId();
        } catch (Exception e) {
            log.error("Error saving receipt: {}", e.getMessage());
            throw new RuntimeException("Error saving receipt");
        }
    }

    public Receipt getReceiptById(String id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Receipt not found for ID: {}", id);
                    return new RuntimeException("Receipt not found");
                });
    }

    public int calculatePoints(Receipt receipt) {
        int retailerPoints = calculateRetailerPoints(receipt);
        int totalPoints = calculateTotalPoints(receipt);
        int itemPoints = calculateItemPoints(receipt);
        int datePoints = calculateDatePoints(receipt);

        int total = retailerPoints + totalPoints + itemPoints + datePoints;

        log.debug("Points Breakdown - Retailer: {}, Total: {}, Items: {}, Date: {}, Final Sum: {}",
                retailerPoints, totalPoints, itemPoints, datePoints, total);

        return total;
    }

    private int calculateRetailerPoints(Receipt receipt) {
        return (int) receipt.getRetailer().chars().filter(Character::isLetterOrDigit).count();
    }

    private int calculateTotalPoints(Receipt receipt) {
        double total = receipt.getTotalAmount();
        if (total <= 0) return 0;

        int points = 0;
        if (total == Math.floor(total)) {
            points += 50;
        }
        if (Math.round(total * 100) % 25 == 0) {
            points += 25;
        }
        return points;
    }

    private int calculateItemPoints(Receipt receipt) {
        int points = (receipt.getItems().size() / 2) * 5;
        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                points += Math.ceil(item.getPrice() * 0.2);
            }
        }
        return points;
    }

    private int calculateDatePoints(Receipt receipt) {
        int points = 0;
        if (receipt.getPurchaseDate().getDayOfMonth() % 2 != 0) {
            points += 6;
        }
        LocalTime time = receipt.getPurchaseTime();
        if (time.getHour() >= 14 && time.getHour() < 16) {
            points += 10;
        }
        return points;
    }
}
