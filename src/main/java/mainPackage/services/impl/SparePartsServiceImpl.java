package mainPackage.services.impl;

import mainPackage.errors.SparePartIdNotFoundException;
import mainPackage.models.bindings.SparePartBindingModel;
import mainPackage.models.entities.Model;
import mainPackage.models.entities.SparePart;
import mainPackage.models.services.SparePartServiceModel;
import mainPackage.models.views.SparePartViewModel;
import mainPackage.repositories.SparePartsRepository;
import mainPackage.services.ModelService;
import mainPackage.services.OrderService;
import mainPackage.services.SparePartsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
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
        return sparePartsRepository.findById(id).orElseThrow(()-> new SparePartIdNotFoundException(String.format("No spare part with id:%d",id)));
    }


    @Override
    public List<SparePartViewModel> getSparePartsByBrandNameAndModel(String brandName, String modelName) {
        List<SparePartViewModel> spareParts = sparePartsRepository.getSparePartsByBrandNameAndModel(brandName, modelName)
                .stream().map(sp -> modelMapper.map(sp, SparePartViewModel.class)).collect(Collectors.toList());
        return spareParts;
    }

    @Override
    public List<SparePartViewModel> getSparePartsByBrandNameForAdd(String brandName) {
        List<SparePartViewModel> spareParts = sparePartsRepository
                .getSparePartsBrandName(brandName).stream()
                .map(sp -> modelMapper.map(sp, SparePartViewModel.class)).collect(Collectors.toList());
        return spareParts;
    }

    @Override
    public SparePartViewModel getSparePartById(Long id) {
        SparePart sparePart = sparePartsRepository.findById(id).orElseThrow();
        SparePartViewModel sparePartViewModel = modelMapper.map(sparePart, SparePartViewModel.class);
        sparePartViewModel.setBrand(sparePart.getModel().getBrand().getBrandName());
        return sparePartViewModel;
    }

    @Override
    public void edit(Long id, SparePartBindingModel sparePartBindingModel) {
        SparePart sparePart = sparePartsRepository.findById(id).orElseThrow(()-> new SparePartIdNotFoundException(String.format("No spare part with id:%d",id)));
        sparePart.setPieces(sparePartBindingModel.getPieces());
        sparePart.setPrice(sparePartBindingModel.getPrice());
        sparePartsRepository.save(sparePart);
    }

    @Override
    public void update(Long id, int i) {
        SparePart sparePart = sparePartsRepository.findById(id).orElseThrow(()-> new SparePartIdNotFoundException(String.format("No spare part with id:%d",id)));
        sparePart.setPieces(0);
        sparePartsRepository.save(sparePart);
    }

    @Override
    public BigDecimal getTotalSparePartPrice(String[] sparePartsId) {
        //TODO make spare parts to set and remove ""
        return sparePartsRepository.getTotalPrice(Arrays.stream(sparePartsId).map(s -> Long.valueOf(s)).toArray(Long[]::new));
    }

    @Override
    public SparePartViewModel getByBrandModelName(String brandName, String modelName, String spName) {
        SparePart sparePart = sparePartsRepository.getByBrandModelName(brandName, modelName, spName);
                SparePartViewModel sparePartViewModel = modelMapper.map(sparePart, SparePartViewModel.class);
        sparePartViewModel.setBrand(sparePart.getModel().getBrand().getBrandName());
        return sparePartViewModel;
    }
}
