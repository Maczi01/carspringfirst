package com.example.carspring.repository;

import com.example.carspring.model.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository {

    List<Car> findAll();

    Optional<Car> getCarById(long id);

    List<Car> getCarsByColor(String color);

    boolean addCar(Car car);

    boolean editCar(Car car);

    boolean editCarParameters(long id, String color, String brand, String model);

    boolean deleteCar (long id);
}
