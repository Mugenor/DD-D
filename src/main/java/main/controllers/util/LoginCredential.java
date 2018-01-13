package main.controllers.util;

public class LoginCredential {
    protected String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
    public LoginCredential(){}
    public LoginCredential(String tokenId){
        this.tokenId = tokenId;
    }
}
