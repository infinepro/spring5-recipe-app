package guru.springframework.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class UnitOfMeasureCommand {

    private Long id;
    private String description;

}
