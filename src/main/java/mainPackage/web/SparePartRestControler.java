package mainPackage.web;

import mainPackage.models.views.SparePartViewModel;
import mainPackage.services.ModelService;
import mainPackage.services.SparePartsService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/spare-parts")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public List<SparePartViewModel> getSparePartsForBrandAndModel(@RequestParam String brandName, @RequestParam String modelName) {
        List<SparePartViewModel> spareParts = sparePartsService.getSparePartsByBrandNameAndModel(brandName, modelName);
        return spareParts;
    }

    @GetMapping("/spare-parts-for-brand")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public List<SparePartViewModel> getSparePartsForBrandForAdd(@RequestParam String brandName) {
        List<SparePartViewModel> spareParts = sparePartsService.getSparePartsByBrandNameForAdd(brandName);
        return spareParts;
    }

    @GetMapping("/spare-part-by-id")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public SparePartViewModel getSparePartsById(@RequestParam Long id) {
        SparePartViewModel sparePart = sparePartsService.getSparePartById(id);
        return sparePart;
    }

    @GetMapping("/spare-parts-totalPrice")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public BigDecimal getTotalSparePartPrice(@RequestParam String[] sparePartsId) {
        if (sparePartsId.length == 0) {
            return BigDecimal.valueOf(0);
        }
        return sparePartsService.getTotalSparePartPrice(sparePartsId);
    }

    @GetMapping("/spare-parts-name")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public SparePartViewModel getByBrandModelAndName(@RequestParam String brandName, @RequestParam String modelName, @RequestParam String spName) {
        return sparePartsService.getByBrandModelName(brandName, modelName, spName);
    }
    @GetMapping("/models")
    @PreAuthorize("hasRole('ROLE_BACK_OFFICE') or hasRole('ROLE_SENIOR')")
    public List<String> getSparePartsForBrand(@RequestParam String brandName) {
        List<String> spareParts = sparePartsService.getSparePartsByBrandName(brandName);
        return spareParts;
    }
}
