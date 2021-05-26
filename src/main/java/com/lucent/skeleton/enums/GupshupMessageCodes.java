package com.lucent.skeleton.enums;

public enum GupshupMessageCodes {

    ENQUEUED(300, "enqueued"),
    SENT(400, "sent"),
    DELIVERED(500, "delivered"),
    READ(600, "read"),
    FAILED(800, "failed"),
    UNDELIVERED(700, "undelivered");

    private final Integer code;
    private final String status;

    GupshupMessageCodes(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() { return code; }

    public String getStatus() { return status; }

    public static GupshupMessageCodes findByStatus(String status) {
        for (GupshupMessageCodes gupshupMessageCodes : GupshupMessageCodes.values()) {
            if (gupshupMessageCodes.getStatus().equalsIgnoreCase(status)) {
                return gupshupMessageCodes;
            }
        }
        return null;
    }
}
