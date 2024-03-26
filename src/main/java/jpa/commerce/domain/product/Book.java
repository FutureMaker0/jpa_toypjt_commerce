package jpa.commerce.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Product {

    private String author;
    private String isbn;
}
