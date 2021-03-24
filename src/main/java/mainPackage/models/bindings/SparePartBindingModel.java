package mainPackage.models.bindings;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class SparePartBindingModel {
    private String brand;
    private String model;
    private String sparePartName;
    private BigDecimal price;
    private int pieces;

    public SparePartBindingModel() {
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
    @Size(min = 1, max = 30,message = "Model name must be between 3 and 20 symbols.")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotBlank(message = "Spare part name can not be empty.")
    @Size(min = 3, max = 20,message = "Spare part name must be between 3 and 20 symbols.")
    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }

    @NotNull(message = "Price can not be empty.")
    @DecimalMin(value = "0", message = "Price must be positive")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Please, enter pieces.")
    @Min(value = 1,message = "Minimum pieces is 1.")
    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
}
