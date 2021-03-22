package mainPackage.services;

import mainPackage.repositories.SparePartsRepository;
import mainPackage.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class SparePartsServiceTest {
    @Autowired
    private SparePartsService sparePartsService;
    @MockBean
    private SparePartsRepository mockSparePartsRepository;
    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void getByBrandAndModel() {
    }

    @Test
    void deleteSparePart() {
    }

    @Test
    void findById() {
    }

    @Test
    void getModelsByBrandName() {
    }

    @Test
    void getSparePartsByBrandNameAndModel() {
    }

    @Test
    void getSparePartsByBrandNameForAdd() {
    }

    @Test
    void getSparePartById() {
    }

    @Test
    void edit() {
    }

    @Test
    void update() {
    }

    @Test
    void getTotalSparePartPrice() {
    }

    @Test
    void getByBrandModelName() {
    }
}