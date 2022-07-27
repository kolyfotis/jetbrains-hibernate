package com.example.exceptions;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DBExcMsg {
    private String message;

    public DBExcMsg() {
    }

    public DBExcMsg(String message) {
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
        return "DBExcMsg{" +
                "message='" + message + '\'' +
                '}';
    }
}
