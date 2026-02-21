package data.Domain;

public enum OrderStatus {
    BOOKED,
    APPROVED, // если в наличии
    AWAITING_PAYMENT,
    PAID,
    AWAITING_DELIVERY,
    READY_FOR_PICKUP,
    ENDED,
    CANCELLED
}
