package com.example.apidemo.service;

import com.example.apidemo.data.Listing;
import com.example.apidemo.data.ListingBasicDetails;
import com.example.apidemo.helper.csvHelper;
import com.example.apidemo.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    ListingRepository repository;

    @Override
    public void save(MultipartFile file){
        try{
            List<Listing> listings = csvHelper.csvTolistings(file.getInputStream());
            repository.saveAll(listings);
        } catch (IOException e) {
            throw new RuntimeException("failed to store csv data: " + e.getMessage());
        }
    }

    @Override
    public void AddListing(Listing listing)
    {
        repository.save(listing);
    }

    @Override
    public List<ListingBasicDetails> getAllListings(Optional<Integer> min_Price, Optional<Integer> max_Price, Optional<Integer> nb_beds) {
        List<ListingBasicDetails> listings = repository.getAllListings(min_Price.get(), max_Price.get(), nb_beds.get());

        return listings;
    }

    @Override
    public Listing getListingInfo(UUID id) {
        return repository.findById(id).get();
    }
}
