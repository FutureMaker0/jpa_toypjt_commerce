package jpa.commerce.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address {

    private String country;
    private String city;
    private String zipcode;

    protected Address() {
    }

    public Address(String country, String city, String zipcode) {
        this.country = country;
        this.city = city;
        this.zipcode = zipcode;
    }
}
