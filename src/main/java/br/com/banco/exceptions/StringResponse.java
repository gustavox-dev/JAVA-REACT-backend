package br.com.banco.exceptions;

public class StringResponse {

    private String message;

    public StringResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
