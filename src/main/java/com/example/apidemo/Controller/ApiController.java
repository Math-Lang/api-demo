package com.example.apidemo.Controller;

import com.example.apidemo.data.ListingBasicDetails;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidemo.service.ApiService;
import com.example.apidemo.helper.csvHelper;
import com.example.apidemo.message.ResponseMessage;
import com.example.apidemo.data.Listing;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {
    @Autowired
    ApiService service;

    /*FIXME: Need to confirm if it is okay for this current API to upload as proper architecture
             would imply have a read API and a write API*/
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
    public ResponseEntity<List<ListingBasicDetails>> getAllListings() {
        try {
            List<ListingBasicDetails> listings = service.getAllListings();

            if(listings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

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



    //This is used for testing purposes
    @GetMapping("/greeting")
	public List<Listing> hello() {
        UUID test = UUID.randomUUID();
		return List.of(
				new Listing(
                        test,
						"Lavaltrie",
						"G7A4X2",
						177,
						1,
						1,
						"Aeriell Pippin",
						5,
						"1 bed/1 bath renovated condo in a vibrant community of Lavaltrie with a balcony and hardwood floor."
				)
		);
	}
}
