package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand convertObject) {

        if (convertObject == null) {
            return null;
        }

        return new UnitOfMeasure()
                .setId(convertObject.getId())
                .setDescription(convertObject.getDescription());

    }
}
