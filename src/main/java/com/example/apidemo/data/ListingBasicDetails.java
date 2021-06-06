package com.example.apidemo.data;

import java.util.UUID;

//Projection of Listing
public interface ListingBasicDetails {
    UUID getId();
    String getPostalCode();
    Integer getPrice();
    Integer getNb_beds();
    Integer getRating();
}
