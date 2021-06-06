package com.example.apidemo.data;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Listing {
    @Id
    @JsonIgnore
    private UUID id;
    private String city;
    private String postalCode;
    @Column(name = "price")
    private Integer price;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNb_beds() {
        return nb_beds;
    }

    public void setNb_beds(Integer nb_beds) {
        this.nb_beds = nb_beds;
    }

    public Integer getNb_baths() {
        return nb_baths;
    }

    public void setNb_baths(Integer nb_baths) {
        this.nb_baths = nb_baths;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", price=" + price +
                ", nb_beds=" + nb_beds +
                ", nb_baths=" + nb_baths +
                ", owner='" + owner + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                '}';
    }
}
