package jpa.commerce.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("C")
public class Concert extends Product {

    private String director;
    private String actor;

}
