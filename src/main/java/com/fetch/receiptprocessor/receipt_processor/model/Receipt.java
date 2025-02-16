package com.fetch.receiptprocessor.receipt_processor.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    private String id;
    private String retailer;
    private LocalDate purchaseDate;
    private LocalTime purchaseTime;
    private double totalAmount;
    private List<Item> items;

}
