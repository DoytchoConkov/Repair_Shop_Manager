package mainPackage.web;

import mainPackage.models.views.SparePartAddViewModel;
import mainPackage.services.SparePartsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<String> getSparePartsForBrandAndModel(@RequestParam String brandName,@RequestParam String modelName) {
        List<String> spareParts = sparePartsService.getSparePartsByBrandNameAndModel(brandName,modelName);
        return spareParts;
    }

    @GetMapping("/spare-parts-add")
    public List<SparePartAddViewModel> getSparePartsForBrandAndModelForAdd(@RequestParam String brandName,@RequestParam String modelName) {
        List<SparePartAddViewModel> spareParts = sparePartsService.getSparePartsByBrandNameAndModelForAdd(brandName,modelName);
        return spareParts;
    }

    @GetMapping("/spare-parts-for-brand")
    public List<SparePartAddViewModel> getSparePartsForBrandForAdd(@RequestParam String brandName) {
        List<SparePartAddViewModel> spareParts = sparePartsService.getSparePartsByBrandNameForAdd(brandName);
        return spareParts;
    }
}
