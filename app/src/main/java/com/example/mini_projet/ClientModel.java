package com.example.mini_projet;

public class ClientModel {
    public ClientModel() {
    }

    private String clientId,clientName,clientEmail,clientPassword;

    public ClientModel(String clientId, String clientName, String clientEmail, String clientPassword) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPassword = clientPassword;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPassword() {
        return clientPassword;
    }
}
