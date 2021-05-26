package com.lucent.skeleton.enums;

public enum TwilioErrorCodes {
    QUEUE_OVERFLOW("QUEUE_OVERFLOW", 30001),
    ACCOUNT_SUSPENDED("ACCOUNT_SUSPENDED", 30002),
    UNREACHABLE_DESTINATION_HANDSET("UNREACHABLE_DESTINATION_HANDSET", 30003),
    MESSAGE_BLOCKED("MESSAGE_BLOCKED", 30004),
    UNKNOWN_DESTINATION_HANDSET("UNKNOWN_DESTINATION_HANDSET", 30005),
    CARRIER_VIOLATION("CARRIER_VIOLATION", 30007),
    UNKNOWN_ERROR("UNKNOWN_ERROR", 30008),
    MISSING_SEGMENT("MISSING_SEGMENT", 30009),
    MESSAGE_PRICE_EXCEEDS_MAX_PRICE("MESSAGE_PRICE_EXCEEDS_MAX_PRICE", 30010);

    private final Integer statusCode;
    private final String status;

    TwilioErrorCodes(String status, Integer statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public static TwilioErrorCodes findByErrorCode(Integer code) {
        for(TwilioErrorCodes twilioErrorCodes: TwilioErrorCodes.values()) {
            if(twilioErrorCodes.getStatusCode().equals(code)) {
                return twilioErrorCodes;
            }
        }
        return TwilioErrorCodes.UNKNOWN_ERROR;
    }
}
