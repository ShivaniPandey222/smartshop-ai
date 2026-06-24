package com.smartshop.product.event;

import java.util.Set;
import java.util.UUID;

public record ProductUpdatedEvent (
    UUID productId,
    String name,
    String description,
    double price,
    int stock,
    String sellerEmail,
    Set<String> categoryNames,
    String eventType
){}
