package com.example.apidemo.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Listing {
    @Id
    @JsonIgnore
    private UUID id;
    private String city;
    private String postalCode;
    @Column(name = "price")
    private Integer price;
    @Column
    private Integer nb_beds;
    private Integer nb_baths;
    private String owner;
    private Integer rating;
    private String description;

    public Listing() {
    }

    public Listing(UUID id, String city, String postalCode, Integer price, Integer nb_beds, Integer nb_baths, String owner, Integer rating, String description) {
        this.id = id;
        this.city = city;
        this.postalCode = postalCode;
        this.price = price;
        this.nb_beds = nb_beds;
        this.nb_baths = nb_baths;
        this.owner = owner;
        this.rating = rating;
        this.description = description;
    }
}
