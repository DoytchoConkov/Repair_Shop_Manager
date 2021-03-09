package мainPackage.services.impl;

import мainPackage.models.entities.Brand;
import мainPackage.models.views.BrandViewModel;
import мainPackage.repositories.BrandRepository;
import мainPackage.services.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Brand getBrand(String brand) {
        Brand brandEntity =brandRepository.findByBrandName(brand).orElse(null);
        if(brandEntity==null){
            brandEntity=new Brand(brand);
            brandEntity=  brandRepository.save(brandEntity);
        }
        return brandEntity;
    }

    @Override
    public List<BrandViewModel> getAll() {
        return brandRepository.findAll().stream().map(b-> modelMapper.map(b,BrandViewModel.class)).collect(Collectors.toList());
    }
}
