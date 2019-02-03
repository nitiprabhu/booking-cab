# booking-cab
This project contains API endpoints, database design for booking cab (initial feature). 

# API provides end-point for the following.

1. Fetch Car details with price when a user enters the source, destination.
2. Book the car when user opts car.
3. An option to cancel the booking, and finally end the trip.
4. An option to raise a concern for the incident happend.
5. An option to view his previous trips travelled so far.

# Technology Used as follows -

1. Programming Language - Java 8.
2. Framework - Spring Boot.
3. Database - Cassandra.
4. API Documentation - Swagger.
5. Other Libraries - Lombok to reduce boilerplate code.

# Database Schema - 


CREATE KEYSPACE carbooking WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE carbooking.user_tracker (
    uid text,
    travel_id text,
    last_updated timestamp,
    car_id text,
    journey_status text,
    PRIMARY KEY (uid, travel_id, last_updated)
) WITH CLUSTERING ORDER BY (travel_id DESC, last_updated DESC);

CREATE TABLE carbooking.car_info (
    car_type text,
    car_name text,
    car_id text,
    car_number text,
    price_factor double,
    PRIMARY KEY (car_type, car_name, car_id)
) WITH CLUSTERING ORDER BY (car_name ASC, car_id ASC);

CREATE TABLE carbooking.driver_info (
    driver_id text PRIMARY KEY,
    driver_email text,
    driver_name text,
    driver_phone text
);

CREATE TABLE carbooking.travel_tracker (
    travel_id text PRIMARY KEY,
    cost double,
    destination text,
    distance_covered int,
    end_time text,
    source text,
    start_time text
);

CREATE TABLE carbooking.user_info (
    uid text PRIMARY KEY,
    email text,
    fname text,
    gender text,
    lname text,
    phone text
);

CREATE TABLE carbooking.price_calculator (
    source text,
    destination text,
    distance int,
    regular_price double,
    PRIMARY KEY (source, destination)
) WITH CLUSTERING ORDER BY (destination ASC);

CREATE TABLE carbooking.incident_tracker (
    incident_id text PRIMARY KEY,
    incident text,
    status text,
    travel_id text,
    uid text
);

# insert queries for testing

insert into carbooking.user_info ( uid , email , fname , lname , gender, phone ) VALUES ( '1', 'a@test.com', 'nitish', 'prabhu', 'M', '9009090900');

 insert into carbooking.car_info (car_type , car_name , car_id , car_number , price_factor ) VALUES ('AC', 'AC-Car1','AC1', '9090', 1.5  );
 insert into carbooking.car_info (car_type , car_name , car_id , car_number , price_factor ) VALUES ('NON_AC', 'NON_AC-Car1','NON_AC1', '9090', 1 );
 insert into carbooking.car_info (car_type , car_name , car_id , car_number , price_factor ) VALUES ('DELUXE', 'DELUXE_Car1','DELUXE1', '9090', 1.8 );

 insert into carbooking.driver_info ( driver_id , driver_email , driver_name , driver_phone ) VALUES ( 'driver_id1','email@driver.com' ,'prabhu', '9008989' );

insert into carbooking.price_calculator (source , destination , distance , regular_price ) VALUES ('koramangala', 'indranagara', 7, 60 );
insert into carbooking.price_calculator (source , destination , distance , regular_price ) VALUES ('indranagara', 'mg_road', 8, 90 );

# visit swagger url which contains API documentation
(Attached sample html page for reference)
url - http://localhost:8080/swagger-ui.html - where 8080 is the port where the application is running.

# Attached postman test file for reference.

