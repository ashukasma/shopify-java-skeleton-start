package com.lucent.skeleton.enums;

public enum MessageStatusCodes {

    INITIATED(200, "Initiated"),
    FAILED_TO_SEND_TO_AGGREGATOR(100, "Failed to aggregator"),
    ENQUEUED(300, "In queue"),
    SENT_BY_AGGREGATOR(400, "Sent by aggregator"),
    DELIVERED(500, "Delivered"),
    READ(600, "Read"),
    OPT_IN_FAILED(700, "Failed to opt in"),
    FAILED(800, "Failed to deliver");

    private final Integer code;
    private final String status;


    MessageStatusCodes(Integer code, String status) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public static MessageStatusCodes findByCode(Integer code) {
        for (MessageStatusCodes messageStatusCodes : MessageStatusCodes.values()) {
            if (code == messageStatusCodes.getCode().intValue()) {
                return messageStatusCodes;
            }
        }
        return null;
    }


}
