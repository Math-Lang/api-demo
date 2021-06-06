package com.example.apidemo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.apidemo.data.Listing;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class csvHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Listing> csvTolistings(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Listing> listings = new ArrayList<Listing>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Listing listing = new Listing(
                        UUID.fromString(csvRecord.get("Id")),
                        csvRecord.get("city"),
                        csvRecord.get("postalCode"),
                        Integer.parseInt(csvRecord.get("price")),
                        Integer.parseInt(csvRecord.get("nb_beds")),
                        Integer.parseInt(csvRecord.get("nb_baths")),
                        csvRecord.get("owner"),
                        Integer.parseInt(csvRecord.get("rating")),
                        csvRecord.get("description")
                );

                listings.add(listing);
            }

            return listings;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
