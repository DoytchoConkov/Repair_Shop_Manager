package mainPackage.services.impl;

import mainPackage.models.entities.ModelEntity;
import mainPackage.models.views.ModelViewModel;
import mainPackage.repositories.ModelRepository;
import mainPackage.services.BrandService;
import mainPackage.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, BrandService brandService, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelEntity getModel(String brand, String model) {
        ModelEntity modelEntity = modelRepository.findByModelName(brand,model);
        if (modelEntity == null) {
            modelEntity = new ModelEntity(model, brandService.getBrand(brand));
            modelEntity = modelRepository.save(modelEntity);
        }
        return modelEntity;
    }

    @Override
    public List<ModelViewModel> getAll() {
        return modelRepository.findAll().stream().map(m->modelMapper.map(m,ModelViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getByBrandName(String brandName) {
        return modelRepository.getByBrandName(brandName);
    }
}
