package com.fetch.receiptprocessor.receipt_processor.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import com.fetch.receiptprocessor.receipt_processor.model.Receipt;

@Repository
public class ReceiptRepository {
    
    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();

    public void save(Receipt receipt) {
        receiptStore.put(receipt.getId(), receipt);
    }

    public Optional<Receipt> findById(String id) {
        return Optional.ofNullable(receiptStore.get(id));
    }
}
