package com.example.busbooking.controller;

import com.example.busbooking.entity.Bus;
import com.example.busbooking.repository.BusRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/buses")
public class BusController {
    private final BusRepository buses; public BusController(BusRepository buses){this.buses=buses;}
    @GetMapping public List<Bus> all(@RequestParam(required=false) String from,@RequestParam(required=false) String to,@RequestParam(required=false) String date){
        if(from!=null&&to!=null&&date!=null)return buses.findByFromCityIgnoreCaseAndToCityIgnoreCaseAndTravelDate(from,to,date); return buses.findAll();
    }
    @GetMapping("/{id}") public Bus one(@PathVariable Long id){return buses.findById(id).orElseThrow();}
}
