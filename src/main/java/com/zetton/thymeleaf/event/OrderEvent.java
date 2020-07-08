package com.zetton.thymeleaf.event;

import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private String message;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderEvent(Object source, String message) {
        super(source);  //强制调用
        this.message = message;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}