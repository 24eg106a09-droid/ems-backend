package com.example.busbooking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bookings")
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private User user;
    @ManyToOne(optional=false) private Bus bus;
    @Column(nullable=false) private String seatNumbers;
    @Column(nullable=false) private Double totalAmount;
    @Column(nullable=false) private String paymentStatus = "PAID";
    @Column(nullable=false) private String bookingStatus = "CONFIRMED";
    private LocalDateTime bookedAt = LocalDateTime.now();
    public Booking() {}
    public Long getId(){return id;} public User getUser(){return user;} public void setUser(User v){user=v;} public Bus getBus(){return bus;} public void setBus(Bus v){bus=v;}
    public String getSeatNumbers(){return seatNumbers;} public void setSeatNumbers(String v){seatNumbers=v;} public Double getTotalAmount(){return totalAmount;} public void setTotalAmount(Double v){totalAmount=v;}
    public String getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(String v){paymentStatus=v;} public String getBookingStatus(){return bookingStatus;} public void setBookingStatus(String v){bookingStatus=v;} public LocalDateTime getBookedAt(){return bookedAt;}
}
