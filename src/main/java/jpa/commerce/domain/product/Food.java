package jpa.commerce.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("F")
public class Food extends Product {

    private String brand;
    private String etc;
}
