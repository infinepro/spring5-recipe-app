package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public List<UnitOfMeasureCommand> getAllUnitOfMeasureCommand() {

        Iterable<UnitOfMeasure> uopIterable = unitOfMeasureRepository.findAll();

        if (uopIterable.iterator().hasNext()) {
            ArrayList<UnitOfMeasure> uopList = (ArrayList<UnitOfMeasure>)uopIterable;
            return uopList
                    .stream()
                    .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Units of measure not exists, please check SQL script or insert data");
        }
    }
}
