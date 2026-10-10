package com.example.busbooking.repository;
import com.example.busbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookingRepository extends JpaRepository<Booking,Long>{ List<Booking> findByUserIdOrderByBookedAtDesc(Long userId); List<Booking> findAllByOrderByBookedAtDesc(); }
