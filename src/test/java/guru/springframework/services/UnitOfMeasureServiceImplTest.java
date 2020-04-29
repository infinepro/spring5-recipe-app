package guru.springframework.services;

import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void testGetAllUnitOfMeasureCommandIfUnitOfMeasureNotFound() {

        ArrayList<UnitOfMeasure> uomList = new ArrayList<>();

        when(unitOfMeasureRepository.findAll()).thenReturn(uomList);

        Assertions.assertThrows(RuntimeException.class, () -> unitOfMeasureService.getAllUnitOfMeasureCommand());
    }

    @Test
    void testGettAllUnitOfMeasureCommand() {
        Long testId = 3L;
        String description = "some description";
        UnitOfMeasure testUom = new UnitOfMeasure()
                .setId(testId)
                .setDescription(description);

        List<UnitOfMeasure> uomList = new ArrayList<>();
        uomList.add(testUom);

        Iterable<UnitOfMeasure> uomIter = uomList;

        when(unitOfMeasureRepository.findAll()).thenReturn(uomIter);

        Assertions.assertTrue(unitOfMeasureService.getAllUnitOfMeasureCommand().iterator().hasNext());
        Assertions.assertEquals(unitOfMeasureService.getAllUnitOfMeasureCommand().iterator().next().getId() , testId);
    }
}