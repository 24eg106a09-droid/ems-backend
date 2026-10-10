package com.example.busbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name="buses")
public class Bus {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String busName;
    @Column(nullable=false) private String busNumber;
    @Column(nullable=false) private String fromCity;
    @Column(nullable=false) private String toCity;
    @Column(nullable=false) private String travelDate;
    @Column(nullable=false) private String departureTime;
    @Column(nullable=false) private String arrivalTime;
    @Column(nullable=false) private Double fare;
    @Column(nullable=false) private Integer totalSeats = 40;
    public Bus() {}
    public Long getId(){return id;} public String getBusName(){return busName;} public void setBusName(String v){busName=v;}
    public String getBusNumber(){return busNumber;} public void setBusNumber(String v){busNumber=v;} public String getFromCity(){return fromCity;} public void setFromCity(String v){fromCity=v;}
    public String getToCity(){return toCity;} public void setToCity(String v){toCity=v;} public String getTravelDate(){return travelDate;} public void setTravelDate(String v){travelDate=v;}
    public String getDepartureTime(){return departureTime;} public void setDepartureTime(String v){departureTime=v;} public String getArrivalTime(){return arrivalTime;} public void setArrivalTime(String v){arrivalTime=v;}
    public Double getFare(){return fare;} public void setFare(Double v){fare=v;} public Integer getTotalSeats(){return totalSeats;} public void setTotalSeats(Integer v){totalSeats=v;}
}
