package mainPackage.services.impl;

import mainPackage.models.entities.Brand;
import mainPackage.models.entities.Model;
import mainPackage.repositories.ModelRepository;
import mainPackage.services.BrandService;
import mainPackage.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public Model getModel(String brand, String model) {
        Brand brandEntity = brandService.getBrand(brand);
        Model modelEntity = modelRepository.findByModelName(model);
        if (modelEntity == null) {
            modelEntity = new Model(model, brandService.getBrand(brand));
            modelEntity = modelRepository.save(modelEntity);
        }
        return modelEntity;
    }
}
