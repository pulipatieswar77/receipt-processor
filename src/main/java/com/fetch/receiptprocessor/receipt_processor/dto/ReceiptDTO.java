package com.fetch.receiptprocessor.receipt_processor.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fetch.receiptprocessor.receipt_processor.model.Item;

import lombok.Data;

@Data
public class ReceiptDTO {

    private String retailer;
    private LocalDate purchaseDate;
    private LocalTime purchaseTime;

    @JsonProperty("total")
    private double totalAmount;
    
    private List<Item> items;

}
