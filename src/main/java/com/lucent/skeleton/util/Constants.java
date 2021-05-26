package com.lucent.skeleton.util;

public class Constants {

    // DEFAULT TABLE GlobalTemplateTopic

    public static final String ABANDONED_CART = "Hi ${customerName}, Glad you arrived";

    public static final String ORDER_PLACED = "Hi *%1$s*, thank you for your purchase of *%2$s* from *%3$s*. Your order is getting ready and we will notify you when it has been shipped. You can view your order here *%4$s* - *www.lucentinnovation.com*.";

    public static final String ORDER_FULFILLED = "Hi {customerName}, thank you for your purchase of *%2$s* from {storeName}. Your order is getting ready and we will notify you when it has been shipped. You can view your order here {url} - *www.lucentinnovation.com*.";

    public final static Integer waChannelId = 1;
    public final static Integer txtChannelId = 2;
    public final static Integer telegramChannelId = 3;

    public final static String instantProcessing = "Instant";
    public final static String timeProcessing = "Time";

}
