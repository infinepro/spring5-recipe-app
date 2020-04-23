package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    final Long UNIT_OF_MEASURE_ID = 3L;
    final String DESCRIPTION_UNIT_OF_MEASURE = "some unit of measure";

    UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom;

    @BeforeEach
    void setUp() {
        uomCommandToUom = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(uomCommandToUom.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(uomCommandToUom.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void testConvertUnitOfMeasureCommandToUnitOfMeasure() {
        //setup data
        UnitOfMeasureCommand testUomCommand = new UnitOfMeasureCommand()
                .setId(UNIT_OF_MEASURE_ID)
                .setDescription(DESCRIPTION_UNIT_OF_MEASURE);

        //convert
        UnitOfMeasure convertUom = uomCommandToUom.convert(testUomCommand);

        //check
        Assertions.assertEquals(convertUom.getId(), testUomCommand.getId());
        Assertions.assertEquals(convertUom.getDescription(), testUomCommand.getDescription());
    }
}