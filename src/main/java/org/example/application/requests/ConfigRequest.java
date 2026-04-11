package org.example.application.requests;

import java.util.UUID;

public record ConfigRequest(UUID carId, UUID decor, UUID steeringWheel, UUID transmission, UUID wheels) { }
