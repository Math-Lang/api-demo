## Rules

Implement an API (HTTP REST) to return information on property listings using [this CSV](tools/rentals.csv).

It is not necessary to implement any external service.

Information need to be shown as :

```
curl http://localhost:8080/rentals/{id}
{
    "postalcode": "G3A0G4",
    "city": "montreal",
    "description": "a very cool description",
    "price": 57,
    "owner": "Harry Potter",
    "nb_beds": 2,
    "nb_baths": 1,
    "rating": 4
}
```

```
curl http://localhost:8080/rentals
[
    {
        "id": "guid are terrible ids",
        "postalcode": "G3A0G4",
        "price": 57,
        "nb_beds": 2,
        "rating": 4
    },
    ...
]
```

Then I need to implement :
- a filter to remove listings with less than X beds.
- a filter to remove listings that do not meet a postal code criteria (ex: G3A\_\_4) where '_' can equal any letter or number. 
- a filter to remove listings below X price
- a filter to remove listings above X price
- be able to apply multiple filters in a request.

## Structure

### Listing
Listing is our data model to create listings.

Projections were implemented in order to sort the information to show. (Basic vs Detailed)

### ApiController
Being a demo, the name is very generic and as it implies this is the API's controller. Currently, the controller allows to read **AND** write which is not necessarily ideal, but based
on the fact that I do not use an external DB, I judged it necessary.

### Helper
Helper is used to read a csv and "translate" it into a list of listings that can then be used.

### Service & Service Impl
The service is an interface containing the methods used in the Controller & said methods and implemented in Service Impl in order to minimize coupling.

### Tests
Junit is currently used for unit tests. While this may not be optimal, it allows to test the multiple possible queries.

### TODO
- Entities are currently exposed. This creates strong coupling. Need to implement DTOs ??. **Would be necessary for the view aspect (if/when front-end is to be implemented**)
- Hide repo CRUD methods (mostly delete). Possible using @override & @RestResource(exported = false) annotations. Make sure to use them on both delete (GUID id & Listing listing). **This was not currently requested
  and therefore may be out of scope, but is good to keep in mind**

### Technical Development Journal
- Basic template was developped using https://bezkoder.com/spring-boot-download-csv-file/ as a reference. This seemed like a perfect template to fulfill the needs of my current API.


- I had issues with the repo. since the template I was using required me to have an external DB. As it is stated that it is not necessary to implement any external service, I looked for an alternate solution. 
  I stumbled across ***H2-database*** which fits my needs perfectly by allowing me to use an in-memory DB.
  I currently need to generate my db content by first uploading my CSV in my repo. which is not ideal considering an API should either read or write (best practices), but this will do for now.
  
  
- I noticed different requests had different outputs. Therefore, I did a little *R&D* of my own and found out about *Projections* which allowed me to control the output with the use of custom get functions in my repo using *JPQL* queries.
  [Thank you Baeldung !](https://www.baeldung.com/spring-data-rest-projections-excerpts)
  

- I implemented the use of filters while making sure it was possible to use multiple filters together through the help of optional **PathParams** and cleaned the ApiService class by separating the class & the implementation to reduce coupling.


- I had to create custom queries in my repository. This was due to the fact that i use a projection for my listing and therefore cannot apply a standard JPA query (Repo of Listing, but returning ListingBasicDetails). While this is not as clean as I would've liked. It works and is still clean & readable.


- I also opted to remain in a single-module project based on the fact that this is a small project.


- As i was moving forward using multiple custom queries in my repository combined with a bunch of *if* statements in my controller and another bunch of *if* statements in my service, I came to conclusion that the more filter I was going to add, the more complex
  my controller and my service were going to get exponentially. I opted for a single query with multiple optional params with default values which seems to be a standard & good practice. This also allowed me to remove the multiple instances of *if statements* &
  the need for custom queries.
  

- While finishing the unit tests, I realized that there may have been some better alternative as my service is not being tested & I feel like tests are not necessarily as solid as they could be, but they test my queries as intended which is the main purpose.