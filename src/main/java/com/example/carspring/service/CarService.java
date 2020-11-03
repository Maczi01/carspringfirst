package com.example.carspring.service;

import com.example.carspring.model.Car;
import com.example.carspring.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService implements CarRepository {

    private List<Car> cars;

    public CarService() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Kamaz", "6540", "yellow"));
        cars.add(new Car(2L, "Star", "66", "blue"));
        cars.add(new Car(3L, "Robur", "LD-3001", "red"));
        cars.add(new Car(4L, "Nysa", "4", "yellow"));
    }

    @Override
    public List<Car> findAll() {
        return cars;
    }

    @Override
    public Optional<Car> getCarById(long id) {
        return cars.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        return cars.stream()
                .filter(e -> e.getColor().toLowerCase().equals(color.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addCar(Car car) {
        return cars.add(car);
    }

    @Override
    public boolean editCar(Car car) {
        Optional<Car> optionalCar = cars.stream()
                .filter(e -> e.getId() == car.getId())
                .findFirst();
        if (optionalCar.isPresent()) {
            cars.set((int) optionalCar.get().getId(), car);
        }
        return false;
    }

    @Override
    public boolean editCarParameters(long id, String color, String brand, String model) {
        Optional<Car> optionalCar = cars.stream().filter(e -> e.getId() == id).findFirst();
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            if (Objects.nonNull(color)) {
                car.setColor(color);
            }
            if (Objects.nonNull(brand)) {
                car.setBrand(brand);
            }
            if (Objects.nonNull(model)) {
                car.setModel(model);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(long id) {
        Optional<Car> optionalCar = cars.stream().filter(e -> e.getId() == id).findFirst();
        return cars.remove(optionalCar.get());
    }
}
