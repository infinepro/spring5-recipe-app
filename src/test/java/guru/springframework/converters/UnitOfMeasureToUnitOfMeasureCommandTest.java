package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    final Long UNIT_OF_MEASURE_ID = 3L;
    final String DESCRIPTION_UNIT_OF_MEASURE = "some unit of measure";

    UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    @BeforeEach
    void setUp() {
        uomToUomCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testConvertNullObject() {
        Assertions.assertNull(uomToUomCommand.convert(null));
    }

    @Test
    void testConvertEmptyObject() {
        Assertions.assertNotNull(uomToUomCommand.convert(new UnitOfMeasure()));
    }

    @Test
    void testConvertConvertUnitOfMeasureToUnitOfMeasureCommand() {
        //setup data
        UnitOfMeasure testUom = new UnitOfMeasure()
                .setId(UNIT_OF_MEASURE_ID)
                .setDescription(DESCRIPTION_UNIT_OF_MEASURE);

        //convert
        UnitOfMeasureCommand convertUomCommand = uomToUomCommand.convert(testUom);

        //check
        Assertions.assertEquals(convertUomCommand.getId(), testUom.getId());
        Assertions.assertEquals(convertUomCommand.getDescription(), testUom.getDescription());
    }

}