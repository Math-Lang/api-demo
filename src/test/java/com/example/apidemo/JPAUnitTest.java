package com.example.apidemo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.apidemo.data.Listing;
import com.example.apidemo.data.ListingBasicDetails;
import com.example.apidemo.repository.ListingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ListingRepository repository;

    @Test
    public void no_listing_if_repo_is_empty() {
        Iterable<Listing> listings = repository.findAll();

        assertThat(listings).isEmpty();
    }

    @Test
    public void save_listing() {
        Listing listing = repository.save(new Listing(UUID.randomUUID(), "Quebec", "G3J9B6", 180, 2,
                                    2, "John Doe", 3, "This is a listing"));
        assertThat(listing).hasFieldOrProperty("id");
        assertThat(listing).hasFieldOrPropertyWithValue("city", "Quebec");
        assertThat(listing).hasFieldOrPropertyWithValue("postalCode", "G3J9B6");
        assertThat(listing).hasFieldOrPropertyWithValue("price", 180);
        assertThat(listing).hasFieldOrPropertyWithValue("nb_beds", 2);
        assertThat(listing).hasFieldOrPropertyWithValue("nb_baths", 2);
        assertThat(listing).hasFieldOrPropertyWithValue("owner", "John Doe");
        assertThat(listing).hasFieldOrPropertyWithValue("rating", 3);
        assertThat(listing).hasFieldOrPropertyWithValue("description", "This is a listing");
    }

    @Test
    public void find_all_listings() {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);

        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);


        Listing listing3 = new Listing(UUID.randomUUID(), "Rimouski", "J4F0C6", 90, 3,
                2, "Bob Dillan", 3, "listing3_desc");
        entityManager.persist(listing3);


        Iterable<Listing> listings = repository.findAll();

        assertThat(listings).hasSize(3).contains(listing1, listing2, listing3);
    }

    @Test
    public void find_listing_by_id() {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);


        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        Listing listing = repository.findById(listing1.getId()).get();

        assertThat(listing).isEqualTo(listing1);
    }

    @Test
    public void apply_postalCode_filter () {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);


        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        List<ListingBasicDetails> listings = repository.getAllListings(0, 100000000, 0, "K_____");
        assertThat(listings).hasSize(1);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("postalCode", "K6D2M0");
    }

    @Test
    public void apply_min_Price_filter () {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);


        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        List<ListingBasicDetails> listings = repository.getAllListings(200, 100000000, 0, "%");
        assertThat(listings).hasSize(1);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("price", 220);
    }

    @Test
    public void apply_max_Price_filter () {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);


        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        List<ListingBasicDetails> listings = repository.getAllListings(0, 200, 0, "%");
        assertThat(listings).hasSize(1);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("price", 180);
    }

    @Test
    public void apply_price_range_filter () {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);

        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        Listing listing3 = new Listing(UUID.randomUUID(), "Rimouski", "J6D0K6", 300, 3,
                2, "Darth Maul", 4, "listing3_desc");
        entityManager.persist(listing3);


        List<ListingBasicDetails> listings = repository.getAllListings(160, 250, 0, "%");
        assertThat(listings).hasSize(2);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("price", 180);
        assertThat(listings.get(1)).hasFieldOrPropertyWithValue("price", 220);
    }

    @Test
    public void apply_price_range_and_postalcode_filters () {
        Listing listing1 = new Listing(UUID.randomUUID(), "Quebec", "K6D2M0", 180, 1,
                4, "John Doe", 3, "listing1_desc");
        entityManager.persist(listing1);

        Listing listing2 = new Listing(UUID.randomUUID(), "Levis", "L6S6J9", 220, 2,
                2, "Luke Skywalker", 3, "listing2_desc");
        entityManager.persist(listing2);

        Listing listing3 = new Listing(UUID.randomUUID(), "Rimouski", "J6D0K6", 300, 3,
                2, "Darth Maul", 4, "listing3_desc");
        entityManager.persist(listing3);


        List<ListingBasicDetails> listings = repository.getAllListings(160, 250, 0, "L6S6J9");
        assertThat(listings).hasSize(1);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("price", 220);
        assertThat(listings.get(0)).hasFieldOrPropertyWithValue("postalCode", "L6S6J9");
    }
}
