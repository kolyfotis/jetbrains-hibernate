/*
* A simple class used to send a String as entity (Object) to a Response
* */
package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
