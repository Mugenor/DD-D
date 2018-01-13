package main.controllers.util;

public class ValidationStatus {
    private boolean valid;

    public ValidationStatus() {
    }

    public ValidationStatus(boolean valid) {

        this.valid = valid;
    }

    public boolean isValid() {

        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
