
package com.example.busbooking.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.busbooking.entity.Booking;
import com.example.busbooking.entity.Bus;
import com.example.busbooking.repository.BookingRepository;
import com.example.busbooking.repository.BusRepository;
import com.example.busbooking.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookings;
    private final BusRepository buses;
    private final UserRepository users;

    public BookingController(
            BookingRepository bookings,
            BusRepository buses,
            UserRepository users) {
        this.bookings = bookings;
        this.buses = buses;
        this.users = users;
    }

    @PostMapping
    public ResponseEntity<?> book(
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        Object userIdObject = session.getAttribute("userId");

        if (userIdObject == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please login first"));
        }

        try {
            Long userId = Long.valueOf(userIdObject.toString());
            Long busId = Long.valueOf(body.get("busId").toString());

            Object seatObject = body.get("seatNumbers");

            if (seatObject == null || seatObject.toString().isBlank()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Please select a seat"));
            }

            String seats = seatObject.toString();

            Set<String> requested = new HashSet<>(
                    Arrays.asList(seats.split(","))
            );

            if (requested.size() != seats.split(",").length) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Duplicate seats selected"));
            }

            Bus bus = buses.findById(busId).orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Bus not found"));

            int seatCount = requested.size();

            if (seatCount == 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Please select a seat"));
            }

            for (Booking old : bookings.findAll()) {
                if (old.getBus() != null
                        && old.getBus().getId().equals(busId)
                        && "CONFIRMED".equals(old.getBookingStatus())) {

                    Set<String> used = new HashSet<>(
                            Arrays.asList(old.getSeatNumbers().split(","))
                    );

                    Set<String> overlap = new HashSet<>(requested);
                    overlap.retainAll(used);

                    if (!overlap.isEmpty()) {
                        return ResponseEntity.badRequest()
                                .body(Map.of(
                                        "message",
                                        "Seat(s) already booked: "
                                                + String.join(", ", overlap)
                                ));
                    }
                }
            }

            Booking booking = new Booking();
            booking.setUser(users.findById(userId).orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User not found")));
            booking.setBus(bus);
            booking.setSeatNumbers(seats);
            booking.setTotalAmount(bus.getFare() * seatCount);

            bookings.save(booking);

            return ResponseEntity.ok(Map.of(
                    "message", "Booking confirmed",
                    "bookingId", booking.getId(),
                    "amount", booking.getTotalAmount()
            ));

        } catch (NumberFormatException | NullPointerException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid bus or seat details"));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> mine(HttpSession session) {

        Object id = session.getAttribute("userId");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please login first"));
        }

        return ResponseEntity.ok(
                bookings.findByUserIdOrderByBookedAtDesc(
                        Long.valueOf(id.toString())
                )
        );
    }
}