package com.example.busbooking.config;

import com.example.busbooking.entity.Bus;
import com.example.busbooking.entity.User;
import com.example.busbooking.repository.BusRepository;
import com.example.busbooking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(UserRepository users, BusRepository buses) {
        return args -> {
            if (users.findByEmail("admin@busbook.com").isEmpty()) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail("admin@busbook.com");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                users.save(admin);
            }
            if (buses.count() == 0) {
                buses.save(bus("Hyderabad Express", "TS09AB1234", "Hyderabad", "Vijayawada", "2026-09-27", "08:00", "14:00", 550.0, 40));
                buses.save(bus("City Travels", "AP16CD5678", "Hyderabad", "Warangal", "2026-09-27", "09:30", "12:30", 300.0, 40));
            }
        };
    }
    private Bus bus(String name,String number,String from,String to,String date,String dep,String arr,double fare,int seats){
        Bus b=new Bus(); b.setBusName(name); b.setBusNumber(number); b.setFromCity(from); b.setToCity(to); b.setTravelDate(date); b.setDepartureTime(dep); b.setArrivalTime(arr); b.setFare(fare); b.setTotalSeats(seats); return b;
    }
}
