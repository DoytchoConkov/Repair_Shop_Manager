package mainPackage.services.impl;

import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.repositories.SparePartsRepository;
import mainPackage.services.ModelService;
import mainPackage.services.SparePartsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartsServiceImpl implements SparePartsService {
    private final SparePartsRepository sparePartsRepository;
    private final ModelService modelService;
    private final ModelMapper modelMapper;

    public SparePartsServiceImpl(SparePartsRepository sparePartsRepository, ModelService modelService, ModelMapper modelMapper) {
        this.sparePartsRepository = sparePartsRepository;
        this.modelService = modelService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SparePartViewModel> getAll() {
        return sparePartsRepository.findAll().stream().map(sp -> {
            SparePartViewModel sparePartViewModel = modelMapper.map(sp, SparePartViewModel.class);
            sparePartViewModel.setBrand(sp.getModel().getBrand().getBrandName());
            return sparePartViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void save(SparePartServiceModel sparePart) {
        Model model = modelService.getModel(sparePart.getBrand(), sparePart.getModel());
        SparePart sparePartEntity = sparePartsRepository.findBySparePartNameAndModel(sparePart.getSparePartName(),
                model).orElse(null);
        if (sparePartEntity == null) {
            sparePartEntity = new SparePart(model, sparePart.getSparePartName());
        }
        sparePartEntity.setModel(model);
        int totalPieces = sparePart.getPieces() + sparePartEntity.getPieces();
        BigDecimal totalPrice = sparePart.getPrice().multiply(BigDecimal.valueOf(sparePart.getPieces()))
                .add(sparePartEntity.getPrice().multiply(BigDecimal.valueOf(sparePartEntity.getPieces())));
        sparePartEntity.setPieces(totalPieces);
        sparePartEntity.setPrice(totalPrice.divide(BigDecimal.valueOf(totalPieces), 2));
        sparePartsRepository.save(sparePartEntity);
    }

    @Override
    public SparePartViewModel getById(Long id) {
        SparePart sparePart = sparePartsRepository.findById(id).orElseThrow();
        SparePartViewModel sparePartViewModel = modelMapper.map(sparePart, SparePartViewModel.class);
        sparePartViewModel.setBrand(sparePart.getModel().getBrand().getBrandName());
        return sparePartViewModel;
    }

    @Override
    public List<SparePartViewModel> getByBrandAndModel(String brandName, String modelName) {
        List<SparePart> spareParts = sparePartsRepository.findByBrandAndModel(brandName, modelName);
        return spareParts.stream().map(sp -> modelMapper.map(sp, SparePartViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteSparePart(Long id) {
        sparePartsRepository.deleteById(id);
    }

    @Override
    public SparePart findById(Long id) {
        return sparePartsRepository.findById(id).orElseThrow();
    }

    @Override
    public void update(SparePart sparePart) {
        sparePartsRepository.save(sparePart);
    }
}
