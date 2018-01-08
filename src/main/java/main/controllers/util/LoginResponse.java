package main.controllers.util;

public class LoginResponse {
    private String status;
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

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
