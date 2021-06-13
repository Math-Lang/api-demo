package com.example.apidemo.repository;



import com.example.apidemo.data.Listing;
import com.example.apidemo.data.ListingBasicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID> {

    @Query("SELECT l FROM Listing l WHERE l.price > ?1 and l.price < ?2 and l.nb_beds > ?3 and l.postalCode LIKE ?4")
    List<ListingBasicDetails> getAllListings(Integer min_Price, Integer max_Price, Integer nb_beds, String postalCode);
}