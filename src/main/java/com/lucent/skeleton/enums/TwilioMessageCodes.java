package com.lucent.skeleton.enums;

public enum TwilioMessageCodes {

    ACCEPTED("accepted", 200),
    QUEUED("queued", 300),
    SENDING("sending", 900),
    SENT("sent", 400),
    RECEIVING("receiving", 100),
    DELIVERED("delivered", 500),
    UNDELIVERED("undelivered", 700),
    READ("read", 600),
    UNKNOWN("unknown", 800);

    private final String status;
    private final int code;

    TwilioMessageCodes(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public static TwilioMessageCodes findByStatus(String status) {
        for(TwilioMessageCodes twilioMessageCodes: TwilioMessageCodes.values()) {
            if(twilioMessageCodes.getStatus().equalsIgnoreCase(status)) {
                return twilioMessageCodes;
            }
        }
        return TwilioMessageCodes.UNKNOWN;
    }
}
