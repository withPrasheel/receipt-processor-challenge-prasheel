package com.assessment.receiptprocessor.service;

import com.assessment.receiptprocessor.helper.InvalidReceiptException;
import com.assessment.receiptprocessor.model.Item;
import com.assessment.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPoints = new ConcurrentHashMap<>();

    private BigInteger counter = BigInteger.ONE;

    public String processReceipt(Receipt receipt) {
        String id;
        if (!isReceiptValid(receipt)) {
            throw new InvalidReceiptException("The receipt is invalid");
        }
        synchronized (this) {
            id = generateSequentialId(counter);
            counter = counter.add(BigInteger.ONE); // increment the counter
        }
        int points = calculatePoints(receipt);
        receiptPoints.put(id, points);
        return id;
    }

    private boolean isReceiptValid(Receipt receipt) {
        // Change the logic in case any extra validation is required
        return true;
    }

    private String generateSequentialId(BigInteger count) {
        String hex = String.format("%032x", count);
        return String.format("%s-%s-%s-%s-%s",
                hex.substring(0, 8),
                hex.substring(8, 12),
                hex.substring(12, 16),
                hex.substring(16, 20),
                hex.substring(20)
        );
    }


    public Integer getPoints(String id) {
        return receiptPoints.get(id);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;
    
        // One point for every alphanumeric character in the retailer name.
        String retailerName = receipt.getRetailer();
        int alphanumericCount = retailerName.replaceAll("[^A-Za-z0-9]", "").length();
        points += alphanumericCount;
    
        // 50 points if the total is a round dollar amount with no cents.
        BigDecimal totalAmount = new BigDecimal(receipt.getTotal());
        if (totalAmount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            points += 50;
        }
    
        // 25 points if the total is a multiple of 0.25.
        if (totalAmount.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }
    
        // 5 points for every two items on the receipt.
        int numberOfItems = receipt.getItems().size();
        int itemPairs = numberOfItems / 2;
        int rule4Points = itemPairs * 5;
        points += rule4Points;
    
        // Points based on item description length.
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                BigDecimal multiplied = price.multiply(new BigDecimal("0.2"));
                int itemPoints = multiplied.setScale(0, RoundingMode.UP).intValue();
                points += itemPoints;
            }
        }
    
        // 6 points if the day in the purchase date is odd.
        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2].trim());
        if (day % 2 != 0) {
            points += 6;
        }
    
        // 10 points if the time is after 2:00pm and before 4:00pm.
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        LocalTime startTime = LocalTime.of(14, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        if (purchaseTime.isAfter(startTime) && purchaseTime.isBefore(endTime)) {
            points += 10;
        } 
    
        return points;
    }    

   }

