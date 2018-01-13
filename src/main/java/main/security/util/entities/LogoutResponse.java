package main.security.util.entities;

public class LogoutResponse {
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "{\"result\":\"" + result + "\"}";
    }
}
