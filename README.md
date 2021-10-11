# Casumo Video Rental Store System

##	Introduction
This document describes Casumo Video Rental Store System. The video rental store is a system for managing the rental
administration using REST API.

This document is intended to be read by people who want to get insights how video rental store system works. 


#	Overview
##	Purpose of the Software
The primary scope of the component is to have an inventory of films and calculate the price for rentals.


# Software Design

## Technology Stack
1. Java SE 8 
2. Spring Boot
3. Apache Tomcat
4. Apache Maven
##	Actors
1. Administrator <br />
   Is someone which invokes the REST API to calculate the price for renting one or several films, and calculating surcharges for films returning.


## FUNCTIONAL DESCRIPTION

### Overview of Epics

 An overview of the epics is
depicted below:

#### Process Phases of Use
<!-- markdownlint-disable -->
| # | Epic | Description |
|---:|--------|--------------|
| 1 | Renting Films | Functions related to renting and returning films. |
| 2 | Renting Price Calculations | Functions related to calculation of prices for renting and returning films. |
| 3 | Film Inventory | Functions related to films and renting storage. |
<!-- markdownlint-enable-->

## Overview of User Stories

The requirements the Database Access System must satisfy, and which define its
functional scope, are formulated below in the usual format of a user story:

"As &lt;stakeholder&gt;, I want &lt;goal&gt;, so that &lt;reason&gt;."


## Use Cases

List of use cases
1. Use Case Rent Films
1. Use Case Return Films

![Insert Data Use Case](rent.png?sanitize=true)
![Update Data Use Case](returnFilms.png?sanitize=true)


##Implemented Use Cases
###	Use Case Rent Films
API Endpoint:
-	Method: POST /rent
-	Body: { “customerId”: “<< customerId >>”, “timestamp”: “<< timestamp >>”, "films" : \[ "<< films >>" \] }
-	Authentication: none

Steps:
1. Verify whether the provided customerId exists
      1. If customerId does not exist, return HTTP 400
2. Verify whether the provided films exit
   1. If films does not exist, return HTTP 400
3. Verify whether the number of days to rent films are not less than 1
   2. If number of days are not okay, return HTTP 400
4. Save the rental and return a rental id
5. Calculate the price of rental and return

Example:
Request
```
curl --location --request POST 'http://localhost:8080/api/v1/rent' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerId": "4",
    "timestamp": 1633857435780,
    "films": [
        {
            "filmId": "1",
            "days": 3
        },
        {
            "filmId": "2",
            "days": 5
        },
        {
            "filmId": "3",
            "days": 6
        }
    ]
}'
```
Response:
```
{
    "totalPrice": 270,
    "rentalId": "1ea275d3-23d2-4576-bef5-88730201bd8e",
    "films": [
        {
            "filmId": "1",
            "days": 3,
            "price": 120
        },
        {
            "filmId": "2",
            "days": 5,
            "price": 90
        },
        {
            "filmId": "3",
            "days": 6,
            "price": 60
        }
    ]
}
```

###	Use Case Return Films

API Endpoint:
-	Method: POST /return
-	Body: { “rentalId”: “<< rentalId >>”, “timestamp”: “<< timestamp >>”, "films" : \[ "<< films >>" \] }
-	Authentication: none

Steps:
1. Verify whether the provided rentalId exists
    1. If rentalId does not exist, return HTTP 400
2. Verify whether the provided films exit
    1. If films does not exist, return HTTP 400
3. Calculate days rented for each film
4. Calculate the surcharges of rental and return

Example: Request
```
curl --location --request POST 'http://localhost:8080/api/v1/return' \
--header 'Content-Type: application/json' \
--data-raw '{
    
    "rentalId" : "1ea275d3-23d2-4576-bef5-88730201bd8e",
    "timestamp" : 1634379071000,
    "films" : [
        {
            "filmId" : "1"
        },
        {
            "filmId" : "2"
        },
        {
            "filmId" : "3"
        }
        
    ]
}'
```
Response:
```
{
    "totalPrice": 150,
    "films": [
        {
            "filmId": "1",
            "days": 3,
            "price": 120
        },
        {
            "filmId": "2",
            "days": 1,
            "price": 30
        },
        {
            "filmId": "3",
            "days": 0,
            "price": 0
        }
    ]
}
```

##	API
The API is REST based. The OpenAPI specification in YAML format, for API endpoints, can be found in folder (file: rental-access-system-rest-open-api.yaml). The API support versioning as part of the URI.

# Data Model
This component persists data in memory using concurrent maps. </br>

On startup some data are loaded. Three books with ID of 1, 2 and 3. Two customers with customerID of 4 and 5.
To add, data change method initDatabase()  in  RentalRepositoryImpl class.

## Security
##	Authentication
###	Authentication of users
|Role|	Authentication	|Comment|
| ------------- |:-------------:| -----:|
|Anonymous |	None|	 the app uses no authentication for communication|



###	Authorization of users
|Role|	Authorization|	Comment|
| ------------- |:-------------:| -----:|
|Anonymous| 	None|	the app uses no authorization for communication|


###	Logging
Detailed logging of app processes provided. Logfiles retention policy to be determined.

###  Running the application

To run the application execute the following command.

```
mvn spring-boot:run
```