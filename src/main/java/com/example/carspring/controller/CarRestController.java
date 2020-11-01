package com.example.carspring.controller;

import com.example.carspring.model.Car;
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

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car carToAdd) {
        if (cars.add(carToAdd)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping()
    public ResponseEntity editCar(@RequestBody Car carToEdit) {
        Optional<Car> optionalCar = cars
                .stream()
                .filter(e -> e.getId() == carToEdit.getId())
                .findFirst();
        if (optionalCar.isPresent()) {
            cars.remove(optionalCar);
            cars.add(carToEdit);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping()
    public ResponseEntity editColor(@PathVariable long id, @RequestBody Car carToEdit) {
        Optional<Car> optionalCar = cars.stream().filter(e -> e.getId() == id).findFirst();
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setBrand(carToEdit.getBrand());
            car.setModel(carToEdit.getModel());
            car.setColor(carToEdit.getColor());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        Optional<Car> optionalCar = cars.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (optionalCar.isPresent()) {
            cars.remove(optionalCar.get());
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
