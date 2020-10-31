package com.example.carspring.controller;

import com.example.carspring.model.Car;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarRestController {

    private List<Car> cars;

    public CarRestController() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Kamaz", "6540", "yellow"));
        cars.add(new Car(2L, "Star", "66", "blue"));
        cars.add(new Car(3L, "Robur", "LD-3001", "red"));
        cars.add(new Car(4L, "Nysa", "4", "yellow"));
    }

//    @GetMapping
//    public ResponseEntity<List<Car>> getAllCars() {
//        return new ResponseEntity<>(cars, HttpStatus.OK);
//    }

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

    @GetMapping()
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam(required = false) String color) {
        if (Objects.isNull(color)) {
            return ResponseEntity.ok(cars);
        } else {
            List<Car> carsByColor = cars.stream()
                    .filter(e -> e.getColor().equals(color))
                    .collect(Collectors.toList());
            if (carsByColor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(carsByColor);
        }
    }

}
