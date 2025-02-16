package com.fetch.receiptprocessor.receipt_processor.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String shortDescription;
    private double price;
}
