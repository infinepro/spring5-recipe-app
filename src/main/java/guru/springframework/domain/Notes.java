package guru.springframework.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.bytecode.enhance.spi.interceptor.AbstractLazyLoadInterceptor;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Recipe recipe;
    private String recipeNotes;

    public Notes() {
    }

}
