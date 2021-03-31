package mainPackage.models.services;

public class OrderReceiveServiceModel {
    private String serialNumber;
    private String brand;
    private String model;
    private String damage;
    private ClientServiceModel client;
    private String note;
    private String clientNote;

    public OrderReceiveServiceModel() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public ClientServiceModel getClient() {
        return client;
    }

    public void setClient(ClientServiceModel client) {
        this.client = client;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }
}
