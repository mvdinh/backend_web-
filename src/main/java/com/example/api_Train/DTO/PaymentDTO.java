package com.example.api_Train.DTO;

import java.io.Serializable;

//@Getter
//@Setter
public class PaymentDTO implements Serializable {
    private String status;
    private String message;
    private String URL;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
