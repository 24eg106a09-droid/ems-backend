# Bus Ticket Booking System — Java Full Stack

## Technology
- Frontend: HTML, CSS, JavaScript
- Backend: Java 17 + Spring Boot 3.5.5
- REST API: Spring Web
- Database: MySQL
- Persistence: Spring Data JPA / Hibernate
- Build: Maven

## Features
### User
Register, login, search buses by route/date, view schedules and fares, select seats, simulate payment, confirm booking, view booking history.

### Admin
Admin login, dashboard statistics, add/delete buses, manage routes/fares through bus records, view all bookings and users through APIs.

## Setup
1. Install Java 17+, Maven, and MySQL.
2. Create the database:
   `CREATE DATABASE bus_booking;`
3. Open `src/main/resources/application.properties` and replace `YOUR_MYSQL_PASSWORD` with your MySQL password.
4. Run from the project folder:
   `mvn spring-boot:run`
5. Open `http://localhost:8080/`.

## Admin account
Register a normal account first. Then run this SQL in MySQL to make it admin:
`UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';`
Then log out/login again and open `/admin.html`.

## Important academic note
The payment step is intentionally a **simulation**; no real payment gateway is connected. Passwords are stored plainly for this beginner/college demo. A production deployment should use Spring Security, password hashing, validation, and a real payment provider.
