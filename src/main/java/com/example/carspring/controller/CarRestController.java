package com.example.carspring.controller;

import com.example.carspring.model.Car;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarRestController {

    private List<Car> cars;

    public CarRestController(List<Car> cars) {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Kamaz", "6540", "yellow"));
        cars.add(new Car(1L, "Star", "66", "blue"));
        cars.add(new Car(1L, "Robur", "LD-3001", "red"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> optionalCar = cars.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (optionalCar.isPresent()) {
            return new ResponseEntity<>(optionalCar.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam String color) {
        List<Car> carsByColor = cars.stream()
                .filter(e -> e.getColor().toLowerCase() == color.toLowerCase())
                .collect(Collectors.toList());
        return new ResponseEntity<>(carsByColor, HttpStatus.NOT_FOUND);
    }

}
