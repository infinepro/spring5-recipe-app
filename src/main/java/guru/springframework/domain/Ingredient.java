package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;

    @OneToOne
    private UnitOfMeasure uop;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    }

}
