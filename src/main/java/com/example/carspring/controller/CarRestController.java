package com.example.carspring.controller;

import com.example.carspring.model.Car;
import com.example.carspring.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cars",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarRestController {

    private CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> optionalCar = carService.getCarById(id);
        if (optionalCar.isPresent()) {
            return new ResponseEntity<>(optionalCar.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam(required = false) String color) {
        if (Objects.isNull(color)) {
            return ResponseEntity.ok(carService.findAll());
        } else {

            return ResponseEntity.ok(carService.getCarsByColor(color));
        }
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car carToAdd) {
        if (carService.addCar(carToAdd)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping()
    public ResponseEntity editCar(@RequestBody Car carToEdit) {
        if (carService.editCar(carToEdit)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity editCarParameters(@PathVariable long id, @RequestBody Car carToEdit) {
        boolean editCar = carService.editCarParameters(id, carToEdit.getColor(), carToEdit.getBrand(), carToEdit.getModel());
        if (editCar) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        if (carService.deleteCar(id)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
