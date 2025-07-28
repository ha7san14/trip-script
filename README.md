# TripScript
## Overview
TripScript is a Java Spring Boot application built for travel enthusiasts to efficiently manage their city breaks and trips. The application allows users to keep track of cities they’ve visited or dream of visiting, and to log trips with details like dates, ratings, and personal notes. It follows a layered architecture (Controller → Service → Repository) and exposes REST APIs for all operations.

## Features
### City Functionalities:

* View All Cities: Fetch a single city using its ID. <br/>
* View City by ID: Add a new customer user along with the account number. <br/>
* Create New City: Add a new city with name, country, and details. <br/>
* Update City: Modify city information. <br/>
* Delete City: Remove a city by ID. <br/>
### Trip Functionalities:

* View All Trips: Get a list of all recorded trips. <br/>
* View Trip by ID: Retrieve specific trip information. <br/>
* Create New Trip: Add a trip (linked to a city) with start/end dates, rating, and notes. <br/>
* Update Trip: Edit a trip’s information. <br/>
* Delete Trip: Delete a trip using its ID. <br/>

## Technology Stack
### Backend:

* Java 21 <br/>
* Java Spring Boot <br/>
* Spring Data JPA <br/>
* Spring Web <br/>
* Lombok <br/>
* Jakarta Validation <br/>
* REST API Design <br/>
* Maven (build tool) <br/>
### Database:

* H2 (In-Memory Database).
### Testing:

* Junit5 <br/>
* Mockito <br/>
* Postman (for API testing) <br/>

### Code Quality Tools:

* Checkstyle <br/>
* PMD (Static Code Analysis) <br/>

