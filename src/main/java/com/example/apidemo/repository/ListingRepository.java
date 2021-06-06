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

    @Query("SELECT l FROM Listing l")
    List<ListingBasicDetails> getAllListings();
}