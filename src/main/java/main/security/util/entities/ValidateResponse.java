package main.security.util.entities;

public class ValidateResponse {
    private boolean valid;
    private String uid;
    private String realm;

    public boolean isValid() {
        return valid;
    }

    public String getUid() {
        return uid;
    }

    public String getRealm() {
        return realm;
    }
    public String toString(){
        return "{\"valid\": " + valid + ", \"uid\": \"" + uid + "\", \"realm\": \"" + realm + "\"}";
    }
}
