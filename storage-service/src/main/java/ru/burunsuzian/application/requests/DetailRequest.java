package ru.burunsuzian.application.requests;

import java.math.BigDecimal;

public record DetailRequest(BigDecimal price, String category) {
}
