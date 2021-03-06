## Ticket Service Homework

This Ticket Service Algorithm facilitates the discovery, temporary hold, and final reservation of seats within a 
high-demand performance venue.

This application is developed using Spring-boot, Spring RESTful webservices, JUnit and Maven.
No Database is used, hence Collections work as repository for storing information of Seats

----------------------------------------------------------------------------------------------------------------------------------------
## Assumptions

* Need to construct Venue using no. of rows and no. of columns
* Users can hold/book any number of seats
* Seats with lower number are best available seats
* Hold time for seats is 60 seconds
* Held seats expire automatically after 60 seconds without any notification to user and user has to resend hold request

```
Acronyms 
A - Available Seats
B - Booked Seats
H - Seats on 60 sec hold

```

----------------------------------------------------------------------------------------------------------------------------------------
## Building the Executing the Project
* Make sure no application is running on port 8080
```
git clone https://github.com/PriyankaMore/TicketBookingAlgorithm.git
cd TicketBookingAlgorithm
./run_ticket_booking.sh
```
* In case the script errors out, please execute each step in the script manually
* The script will run the project, generate venue, hold bookings and provide you with a URL to confirm booking.
* If you wish to manually do the above tasks, the information for each step can be found in the below documentation.


----------------------------------------------------------------------------------------------------------------------------------------
## RESTful web services

* These rest services if tested through curl on terminal window show the image representation of seating arrangement. 

* Build the venue by providing number of rows and number of columns

  ```
  GET http://localhost:8080/generate/10/10
  ```
  ![Image of buildVenue](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/buildVenue.PNG)
  
* Find the best available seats and hold them on behalf of customer. The service requires number of seats as an input parameter.
  Seats can be held for max. 60 seconds. If the held seats expires, Customer has to re-send the request to hold seats.

  ```
  GET http://localhost:8080/holdSeats/5
  ```
  ![Image of HoldSeats](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/holdSeats.PNG)
  
* Reserve and commit a specific group of held seats for a customer. Book the held seats based on holdRefId
  ```
  GET http://localhost:8080/bookSeats/559d691d-a8c4-4b03-b58b-4dc03f1732ea
  ```
  ![Image of BookSeast](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/bookSeats.PNG)
  
* Find the number of seats available within the venue
  ```
  GET http://localhost:8080/getAllSeats
  ```
  ![Image of GetAvailableSeats](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/getAvailableSeats.PNG)
  
* Display the seating arrangement in Venue 
  ```
  GET http://localhost:8080/display
  ```
  ![Image of DisplaySeats](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/displaySeats.PNG)
  -------------------------------------------------------------------------------------------------------------------------------------
  ## Flow Chart
  
  * Overview of Algorithm
  
  ![Image of Overview](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/TicketBookingOverview.PNG)
  
  * Hold Process
  
  ![Image of Overview](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/holdSeatsFlowchart.PNG)
  
  * Thread Scheduling 
  
  ![Image of Overview](https://github.com/PriyankaMore/TicketBookingAlgorithm/blob/master/Image/ThreadOperation.PNG)
  
  
  
