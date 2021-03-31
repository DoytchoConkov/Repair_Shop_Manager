package mainPackage.models.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrderReceiveBindingModel {
    private String serialNumber;
    private String brand;
    private String model;
    private String damage;
    private String note;
    private String clientName;
    private String clientEmail;
    private String clientPhoneNumber;
    private String clientNote;

    public OrderReceiveBindingModel() {
    }

    @NotBlank(message = "Serial number can not be empty.")
    @Size(min = 6, max = 20,message = "Serial number must be between 10 and 20 symbols.")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @NotBlank(message = "Brand can not be empty.")
    @Size(min = 3, max = 20,message = "Brand name must be between 10 and 20 symbols.")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotBlank(message = "Model can not be empty.")
    @Size(min = 1, max = 20,message = "Model name must be between 3 and 20 symbols.")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotBlank(message = "Damage can not be empty.")
    @Size(min = 5, max = 20,message = "Damage must be between 10 and 20 symbols.")
    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @NotBlank(message = "Client name can not be empty.")
    @Size(min = 5, max = 30,message = "Client name must be between 10 and 30 symbols.")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @NotBlank(message = "Client phone number can not be empty.")
    @Size(min = 9, max = 20,message = "Client phone number must be between 9 and 20 symbols.")
    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }
}
