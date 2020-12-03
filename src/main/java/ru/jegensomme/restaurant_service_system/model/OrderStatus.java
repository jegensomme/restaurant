package ru.jegensomme.restaurant_service_system.model;

public enum OrderStatus {
    PROCESSING,
    ON_BILL,
    CLOSED;

    public String toStringRus() {
        String status = "";
        switch (this) {
            case PROCESSING -> status = "Обслуживается";
            case ON_BILL -> status = "На пречеке";
            case CLOSED -> status = "Закрыт";
        }
        return status;
    }
}
