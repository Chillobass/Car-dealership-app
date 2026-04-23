package ru.Burunsuzian.application.requests;

import java.math.BigDecimal;
import java.util.UUID;

public record DetailRequest(BigDecimal price, String category) {
}
