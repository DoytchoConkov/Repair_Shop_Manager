package mainPackage.services.impl;

import mainPackage.models.entities.Damage;
import mainPackage.models.views.DamageViewModel;
import mainPackage.repositories.DamageRepository;
import mainPackage.services.DamageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class DamageServiceTest {
    @Autowired
    private DamageService damageService;
    @MockBean
    private DamageRepository mockDamageRepository;

    @Test
    void getDamage() {
        Damage damage = new Damage("Broken LCD");
        Mockito.when(mockDamageRepository.findByDamageName("Broken LCD")).thenReturn(damage);
        Damage receivedBrand = damageService.getDamage("Broken LCD");
        assertEquals(damage.getDamageName(), receivedBrand.getDamageName());
    }
    @Test
    void getDamageWhenNotExist() {
        Damage damage = new Damage("Broken LCD");
        Mockito.when(mockDamageRepository.findByDamageName("Broken LCD")).thenReturn(null);
        Damage receivedBrand = damageService.getDamage("Broken LCD");
//        assertEquals(damage.getDamageName(), receivedBrand.getDamageName());
        //TODO
    }

    @Test
    void getAll() {
        List<Damage> brands = new ArrayList<>();
        Damage damage = new Damage("Broken Glass");
        brands.add(damage);
        Mockito.when(mockDamageRepository.findAll()).thenReturn(brands);
        List<DamageViewModel> allBrands = damageService.getAll();
        assertEquals(brands.get(0).getDamageName(), allBrands.get(0).getDamageName());
    }
}