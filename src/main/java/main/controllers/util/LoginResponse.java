package main.controllers.util;

public class LoginResponse extends LoginCredential {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginResponse(){}
    public LoginResponse(String status, String tokenId) {
        this.status = status;
        this.tokenId = tokenId;
    }
}
