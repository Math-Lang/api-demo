package com.example.apidemo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidemo.helper.csvHelper;
import com.example.apidemo.data.Listing;
import com.example.apidemo.repository.ListingRepository;

@Service
public class ApiService {

    @Autowired
    ListingRepository repository;

    public void save(MultipartFile file){
        try{
            List<Listing> listings = csvHelper.csvTolistings(file.getInputStream());
            repository.saveAll(listings);
        } catch (IOException e) {
            throw new RuntimeException("failed to store csv data: " + e.getMessage());
        }
    }

    public void AddListing(Listing listing)
    {
        repository.save(listing);
    }

    public List<Listing> getAllListings() {
        List<Listing> listings = new ArrayList<>();
        repository.findAll().forEach(listings::add);
        return listings;
    }
}
