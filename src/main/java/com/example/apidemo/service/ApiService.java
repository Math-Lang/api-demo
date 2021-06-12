package com.example.apidemo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.apidemo.data.ListingBasicDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidemo.helper.csvHelper;
import com.example.apidemo.data.Listing;
import com.example.apidemo.repository.ListingRepository;

public interface ApiService {

    public void save(MultipartFile file);

    public void AddListing(Listing listing);

    public List<ListingBasicDetails> getAllListings(Optional<Integer> min_Price, Optional<Integer> max_Price, Optional<Integer> nb_beds) ;

    public Listing getListingInfo(UUID id);
}
