package mainPackage.web;

import mainPackage.models.views.SparePartViewModel;
import mainPackage.services.SparePartsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/spare-parts")
public class SparePartRestControler {
    private final SparePartsService sparePartsService;

    public SparePartRestControler(SparePartsService sparePartsService) {
        this.sparePartsService = sparePartsService;
    }

    @GetMapping("/models")
    public List<String> getAllModelsForBrand(@RequestParam String brandName) {
        List<String> models = sparePartsService.getModelsByBrandName(brandName);
        return models;
    }

    @GetMapping("/spare-parts")
    public List<SparePartViewModel> getSparePartsForBrandAndModel(@RequestParam String brandName, @RequestParam String modelName) {
        List<SparePartViewModel> spareParts = sparePartsService.getSparePartsByBrandNameAndModel(brandName,modelName);
        return spareParts;
    }

    @GetMapping("/spare-parts-for-brand")
    public List<SparePartViewModel> getSparePartsForBrandForAdd(@RequestParam String brandName) {
        List<SparePartViewModel> spareParts = sparePartsService.getSparePartsByBrandNameForAdd(brandName);
        return spareParts;
    }
    @GetMapping("/spare-part-by-id")
    public SparePartViewModel getSparePartsById(@RequestParam Long id) {
        SparePartViewModel sparePart = sparePartsService.getSparePartById(id);
        return sparePart;
    }
    @GetMapping("/spare-parts-totalPrice")
    public BigDecimal getTotalSparePartPrice(@RequestParam String[] sparePartsId) {
        return sparePartsService.getTotalSparePartPrice(sparePartsId);
    }
    @GetMapping("/spare-parts-name")
    public SparePartViewModel getByBrandfModelName(@RequestParam String brandName, @RequestParam String modelName,@RequestParam String spName) {
        return sparePartsService.getByBrandModelName(brandName,modelName,spName);
    }
}
