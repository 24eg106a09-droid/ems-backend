package com.example.busbooking.controller;

import com.example.busbooking.entity.Booking;
import com.example.busbooking.entity.Bus;
import com.example.busbooking.entity.User;
import com.example.busbooking.repository.BookingRepository;
import com.example.busbooking.repository.BusRepository;
import com.example.busbooking.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BusRepository buses;
    private final BookingRepository bookings;
    private final UserRepository users;

    public AdminController(
            BusRepository buses,
            BookingRepository bookings,
            UserRepository users) {

        this.buses = buses;
        this.bookings = bookings;
        this.users = users;
    }

    // Check whether the logged-in user is an admin
    private void checkAdmin(HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Admin access required"
            );
        }
    }

    // Get all buses
    @GetMapping("/buses")
    public List<Bus> getAllBuses(HttpSession session) {

        checkAdmin(session);

        return buses.findAll();
    }

    // Add a new bus
    @PostMapping("/buses")
    public Bus addBus(
            @RequestBody Bus bus,
            HttpSession session) {

        checkAdmin(session);

        return buses.save(bus);
    }

    // Update bus
    @PutMapping("/buses/{id}")
    public Bus updateBus(
            @PathVariable Long id,
            @RequestBody Bus bus,
            HttpSession session) {

        checkAdmin(session);

        Bus existingBus = buses.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Bus not found"
                        ));

        existingBus.setBusName(bus.getBusName());
        existingBus.setBusNumber(bus.getBusNumber());
        existingBus.setFromCity(bus.getFromCity());
        existingBus.setToCity(bus.getToCity());
        existingBus.setTravelDate(bus.getTravelDate());
        existingBus.setDepartureTime(bus.getDepartureTime());
        existingBus.setArrivalTime(bus.getArrivalTime());
        existingBus.setFare(bus.getFare());
        existingBus.setTotalSeats(bus.getTotalSeats());

        return buses.save(existingBus);
    }

    // Delete bus
    @DeleteMapping("/buses/{id}")
    public Map<String, String> deleteBus(
            @PathVariable Long id,
            HttpSession session) {

        checkAdmin(session);

        if (!buses.existsById(id)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Bus not found"
            );
        }

        buses.deleteById(id);

        return Map.of(
                "message",
                "Bus deleted successfully"
        );
    }

    // Get all bookings
    @GetMapping("/bookings")
    public List<Booking> getAllBookings(
            HttpSession session) {

        checkAdmin(session);

        return bookings.findAllByOrderByBookedAtDesc();
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers(
            HttpSession session) {

        checkAdmin(session);

        return users.findAll();
    }

    // Dashboard statistics
    @GetMapping("/stats")
    public Map<String, Object> getStats(
            HttpSession session) {

        checkAdmin(session);

        return Map.of(
                "buses", buses.count(),
                "users", users.count(),
                "bookings", bookings.count()
        );
    }
}