package com.example.apidemo.controller;

import com.example.apidemo.data.ListingBasicDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidemo.service.ApiService;
import com.example.apidemo.helper.csvHelper;
import com.example.apidemo.message.ResponseMessage;
import com.example.apidemo.data.Listing;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ApiController {
    @Autowired
    ApiService service;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (csvHelper.hasCSVFormat(file)) {
            try {
                service.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() +e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "please upload a csv file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<ListingBasicDetails>> getAllListings(@RequestParam(value = "min_Price", defaultValue = "0") Optional<Integer> min_Price, @RequestParam(value = "max_Price", defaultValue = "100000000") Optional<Integer> max_Price, @RequestParam(value = "nb_beds", defaultValue = "0") Optional<Integer> nb_beds) {
        try {
                List<ListingBasicDetails> listings = service.getAllListings(min_Price, max_Price, nb_beds);

            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "rentals/{id}")
    public ResponseEntity<Listing> getListingInfo(@PathVariable UUID id) {
        try {
            Listing listing = service.getListingInfo(id);

            if(listing == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
